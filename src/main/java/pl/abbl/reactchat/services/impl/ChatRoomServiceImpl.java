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


    @Override
    public AbstractCallback createChatRoom(ChatRoom chatRoom, Principal principal) {
        ChatUser chatUser = userService.getUserInformationByPrincipal(principal);

        System.out.println(chatRoom.toString());

        if(checkIfAllRequiredFieldsAreFilledUp(chatRoom)){
            if(chatRoomRepository.findByName(chatRoom.getName()) == null){
                chatRoom.setName(formatChatRoomName(chatRoom.getName()));
                chatRoom.setOwnerId(chatUser.getId());
                chatRoom.setStatus(ChatRoomStatus.OPEN);
                chatRoomRepository.saveAndFlush(chatRoom);

                setUserRightAsOwnerOfRoom(chatUser, chatRoom.getName());
                contextChangeService.updateUsersOnRoomChange(chatRoom);

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

    private void setUserRightAsOwnerOfRoom(ChatUser chatUser, String roomName){
        ChatRoom chatRoom = chatRoomRepository.findByName(roomName);

        if(chatRoom != null){
            roomRightService.addUserRight(chatUser.getId(), chatRoom.getId(), RoomRightLevel.OWNER);

            return;
        }
        logger.warn("Couldn't set user rights as a owner of ChatRoom, It might not be created before.");
    }

    //TODO Make this function more readable for humans.
    @Override
    public AbstractCallback updateChatRoom(ChatRoom chatRoom, Principal principal) {
        ChatUser chatUser = userService.getUserInformationByPrincipal(principal);
        ChatRoom databaseChatRoom = chatRoomRepository.findById(chatRoom.getId());

        if(databaseChatRoom != null){
            if(roomRightService.isUserRightLevelHighEnough(chatUser.getId(), databaseChatRoom.getId(), RoomRightLevel.MODERATOR)){
                RoomRightLevel userRoomRightLevel = roomRightService.getUserRight(chatUser.getId(), databaseChatRoom.getId()).getRightLevel();

                if(!databaseChatRoom.getName().equals(chatRoom.getName())){
                    if(userRoomRightLevel != RoomRightLevel.OWNER){
                        return new ChatRoomCallback(ChatRoomCallback.INSUFFICIENT_RIGHTS);
                    }else{
                        if(chatRoomRepository.findByName(chatRoom.getName()) != null){
                            return new ChatRoomCallback(ChatRoomCallback.CHAT_ROOM_NAME_TAKEN);
                        }
                        databaseChatRoom.setName(chatRoom.getName());
                    }
                }

                if(databaseChatRoom.getStatus() != chatRoom.getStatus()){
                    if(userRoomRightLevel != RoomRightLevel.OWNER){
                        return new ChatRoomCallback(ChatRoomCallback.INSUFFICIENT_RIGHTS);
                    }
                    databaseChatRoom.setStatus(chatRoom.getStatus());
                }

                if(!databaseChatRoom.getDescription().equals(chatRoom.getDescription())){
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
}
