package pl.abbl.reactchat.service;

import java.util.List;

import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.entity.ChatRoom;

import javax.servlet.http.HttpServletRequest;

public interface ChatRoomsService {
	List<ChatRoom> getPublicChatRooms();
	List<ChatRoom> getPrivateChatRooms(HttpServletRequest request);
	AbstractCallback saveChatRoom();
	ChatRoom isChatRoomPrivate(int roomId);
}
