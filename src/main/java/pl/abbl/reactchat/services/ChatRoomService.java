package pl.abbl.reactchat.services;

import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.entities.ChatRoom;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ChatRoomService {
    AbstractCallback createChatRoom(ChatRoom chatRoom, HttpServletRequest httpServletRequest);
    AbstractCallback updateChatRoom(ChatRoom chatRoom, HttpServletRequest httpServletRequest);
    List<ChatRoom> getUserChatRooms(String username, HttpServletRequest httpServletRequest);
    List<ChatRoom> getPublicChatRooms();
}
