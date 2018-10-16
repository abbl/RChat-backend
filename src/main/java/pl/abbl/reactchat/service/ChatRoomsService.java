package pl.abbl.reactchat.service;

import java.util.List;

import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.entity.ChatRoom;

public interface ChatRoomsService {
	List<ChatRoom> getPublicChatRooms();
	List<ChatRoom> getPrivateChatRooms();
	AbstractCallback saveChatRoom();
	boolean isChatRoomPrivate(int roomId);
}
