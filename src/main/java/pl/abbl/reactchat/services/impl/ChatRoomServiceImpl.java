package pl.abbl.reactchat.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.callbacks.ChatRoomCallback;
import pl.abbl.reactchat.definitions.enums.ChatRoomStatus;
import pl.abbl.reactchat.definitions.enums.ChatRoomType;
import pl.abbl.reactchat.definitions.enums.RoomRightLevel;
import pl.abbl.reactchat.models.ChatRoom;
import pl.abbl.reactchat.models.ChatUser;
import pl.abbl.reactchat.models.RoomRight;
import pl.abbl.reactchat.repositories.ChatContentRepository;
import pl.abbl.reactchat.repositories.ChatRoomRepository;
import pl.abbl.reactchat.services.ChatRoomService;
import pl.abbl.reactchat.services.ContextChangeService;
import pl.abbl.reactchat.services.RoomRightService;
import pl.abbl.reactchat.services.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {
    private static Logger logger = LoggerFactory.getLogger(ChatRoomServiceImpl.class);

    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RoomRightService roomRightService;
    @Autowired
    private ContextChangeService contextChangeService;
    @Autowired
    private ChatContentRepository chatContentRepository;

    @Override
    public AbstractCallback createChatRoom(ChatRoom chatRoom, Principal principal) {
        ChatUser chatUser = userService.getUserInformationByPrincipal(principal);

        if(checkIfAllRequiredFieldsAreFilledUp(chatRoom)){
            if(chatRoomRepository.findByName(chatRoom.getName()) == null){
                chatRoom.setName(formatChatRoomName(chatRoom.getName()));
                chatRoom.setOwnerId(chatUser.getId());
                chatRoom.setStatus(ChatRoomStatus.OPEN);
                chatRoomRepository.saveAndFlush(chatRoom);

                setUserRightAsOwnerOfRoom(chatUser, chatRoom.getName());
                contextChangeService.updateUsersOnRoomChange(chatRoom);
                chatContentRepository.createChatContentTable(chatRoom);

                return new ChatRoomCallback(ChatRoomCallback.CHAT_ROOM_CREATED_SUCCESSFULLY);
            }else{
                return new ChatRoomCallback(ChatRoomCallback.CHAT_ROOM_NAME_TAKEN);
            }
        }else{
            return new ChatRoomCallback(ChatRoomCallback.MISSING_CHAT_ROOM_CREDENTIALS);
        }
    }

    private boolean checkIfAllRequiredFieldsAreFilledUp(ChatRoom chatRoom){
        return chatRoom.getName() != null && chatRoom.getType() != null;
    }

    private String formatChatRoomName(String name){
        StringBuilder stringBuilder = new StringBuilder(name);

        while(stringBuilder.charAt(0) == '#'){
            stringBuilder.deleteCharAt(0);
        }
        stringBuilder.insert(0, '#');

        return stringBuilder.toString();
    }

    private boolean setUserRightAsOwnerOfRoom(ChatUser chatUser, String roomName){
        ChatRoom chatRoom = chatRoomRepository.findByName(roomName);

        if(chatRoom != null){
            roomRightService.addUserRight(chatUser.getId(), chatRoom.getId(), RoomRightLevel.OWNER);
            return true;
        }
        logger.warn("Couldn't set user rights as a owner of ChatRoom, It might not be created before.");
        return false;
    }

    //TODO Make this function more readable for humans.
    @Override
    public AbstractCallback updateChatRoom(ChatRoom chatRoom, Principal principal) {
        ChatUser chatUser = userService.getUserInformationByPrincipal(principal);
        ChatRoom databaseChatRoom = chatRoomRepository.findById(chatRoom.getId());

        if(databaseChatRoom != null){
            if(roomRightService.isUserRightLevelHighEnough(chatUser.getId(), databaseChatRoom.getId(), RoomRightLevel.MODERATOR)){
                RoomRightLevel userRoomRightLevel = roomRightService.getUserRight(chatUser.getId(), databaseChatRoom.getId()).getRightLevel();

                //ChatRoom name update
                if(!databaseChatRoom.getName().equals(chatRoom.getName()) && chatRoom.getName() != null && !chatRoom.getName().isEmpty()){
                    if(userRoomRightLevel != RoomRightLevel.OWNER){
                        return new ChatRoomCallback(ChatRoomCallback.INSUFFICIENT_RIGHTS);
                    }else{
                        if(chatRoomRepository.findByName(chatRoom.getName()) != null){
                            return new ChatRoomCallback(ChatRoomCallback.CHAT_ROOM_NAME_TAKEN);
                        }
                        databaseChatRoom.setName(chatRoom.getName());
                    }
                }

                //ChatRoom status update
                if(databaseChatRoom.getStatus() != chatRoom.getStatus() && chatRoom.getStatus() != null){
                    if(userRoomRightLevel != RoomRightLevel.OWNER){
                        return new ChatRoomCallback(ChatRoomCallback.INSUFFICIENT_RIGHTS);
                    }
                    databaseChatRoom.setStatus(chatRoom.getStatus());
                }

                //ChatRoom desc update
                if(!databaseChatRoom.getDescription().equals(chatRoom.getDescription()) && chatRoom.getDescription() != null){
                    databaseChatRoom.setDescription(chatRoom.getDescription());
                }

                chatRoomRepository.saveAndFlush(databaseChatRoom);
                contextChangeService.updateUsersOnRoomChange(databaseChatRoom);

                return new ChatRoomCallback(ChatRoomCallback.CHAT_ROOM_UPDATED_SUCCESSFULLY);
            }

            return new ChatRoomCallback(ChatRoomCallback.INSUFFICIENT_RIGHTS);
        }
        return new ChatRoomCallback(ChatRoomCallback.CANT_UPDATE_CHAT_ROOM_THAT_DOESNT_EXIST);
    }

    @Override
    public AbstractCallback joinChatRoom(ChatRoom chatRoom, Principal principal) {
        ChatUser chatUser = userService.getUserInformationByPrincipal(principal);

        if(chatRoomRepository.findById(chatRoom.getId()) != null){
            if(!roomRightService.doesUserHaveAnyRight(chatUser.getId(), chatRoom.getId())){
                roomRightService.addUserRight(chatUser.getId(), chatRoom.getId(), RoomRightLevel.PARTICIPANT);

                return new ChatRoomCallback(ChatRoomCallback.SUCCESSFULLY_JOINED_CHAT_ROOM);
            }
            return new ChatRoomCallback(ChatRoomCallback.YOU_ARE_ALREADY_MEMBER_OF_THIS_CHANNEL);
        }
        return new ChatRoomCallback(ChatRoomCallback.NO_SUCH_CHAT_ROOM_FIND);
    }

    @Override
    public List<ChatRoom> getUserChatRooms(Principal principal) {
        ChatUser chatUser = userService.getUserInformationByPrincipal(principal);
        List<Integer> chatRooms = new ArrayList<>();

        for (RoomRight roomRight : roomRightService.getAllUserRights(chatUser.getId())) {
            chatRooms.add(roomRight.getRoomId());
        }

        if(!chatRooms.isEmpty())
            return chatRoomRepository.findChatRoomsByListOfId(chatRooms);

        return new ArrayList<>();
    }

    @Override
    public List<ChatRoom> getPublicChatRooms() {
        List<ChatRoom> publicChatRooms = new ArrayList<>();

        for(ChatRoom chatRoom : chatRoomRepository.findAll()){
            if(chatRoom.getType() == ChatRoomType.PUBLIC){
                publicChatRooms.add(chatRoom);
            }
        }
        return publicChatRooms;
    }

    @Override
    public List<ChatRoom> getChatRoomByListOfId(List<Integer> idList) {
        return chatRoomRepository.findChatRoomsByListOfId(idList);
    }

    @Override
    public ChatRoom getChatRoomById(int id) {
        return chatRoomRepository.findById(id);
    }
}
