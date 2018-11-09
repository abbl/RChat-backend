package pl.abbl.reactchat.services.impl;

import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.entities.ChatRoom;
import pl.abbl.reactchat.services.ChatRoomService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ChatRoomServiceImpl implements ChatRoomService {
    @Override
    public AbstractCallback createChatRoom(ChatRoom chatRoom, HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public AbstractCallback updateChatRoom(ChatRoom chatRoom, HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public List<ChatRoom> getUserChatRooms(String username, HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public List<ChatRoom> getPublicChatRooms() {
        return null;
    }
}
