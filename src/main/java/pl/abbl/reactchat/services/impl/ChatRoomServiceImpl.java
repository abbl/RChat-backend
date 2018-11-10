package pl.abbl.reactchat.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.callbacks.ChatRoomCallback;
import pl.abbl.reactchat.definitions.enums.ChatRoomType;
import pl.abbl.reactchat.entities.ChatRoom;
import pl.abbl.reactchat.entities.ChatUser;
import pl.abbl.reactchat.entities.RoomRight;
import pl.abbl.reactchat.repositories.ChatRoomRepository;
import pl.abbl.reactchat.services.ChatRoomService;
import pl.abbl.reactchat.services.RoomRightService;
import pl.abbl.reactchat.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

public class ChatRoomServiceImpl implements ChatRoomService {
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RoomRightService roomRightService;

    @Override
    public AbstractCallback createChatRoom(ChatRoom chatRoom, Principal principal) {
        ChatUser chatUser = userService.getUserInformationByPrincipal(principal);

        if(checkIfAllRequiredFieldsAreFilledUp(chatRoom)){
            if(chatRoomRepository.findByName(chatRoom.getName()) == null){
                chatRoom.setName(formatChatRoomName(chatRoom.getName()));
                chatRoomRepository.saveAndFlush(chatRoom);

                return new ChatRoomCallback(ChatRoomCallback.CHAT_ROOM_CREATED_SUCCESSFULLY);
            }else{
                return new ChatRoomCallback(ChatRoomCallback.CHAT_ROOM_NAME_TAKEN);
            }
        }else{
            return new ChatRoomCallback(ChatRoomCallback.MISSING_CHAT_ROOM_CREDENTIALS);
        }
    }

    private boolean checkIfAllRequiredFieldsAreFilledUp(ChatRoom chatRoom){
        return chatRoom.getName() != null && chatRoom.getType() != null && chatRoom.getId() == 0;
    }

    private String formatChatRoomName(String name){
        StringBuilder stringBuilder = new StringBuilder(name);

        while(stringBuilder.charAt(0) == '#'){
            stringBuilder.deleteCharAt(0);
        }
        stringBuilder.insert(0, '#');

        return stringBuilder.toString();
    }

    @Override
    public AbstractCallback updateChatRoom(ChatRoom chatRoom, Principal principal) {
        //TODO
        return null;
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
