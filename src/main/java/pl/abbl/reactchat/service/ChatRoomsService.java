package pl.abbl.reactchat.service;

import java.util.List;

import pl.abbl.reactchat.entity.ChatRoom;

public interface ChatRoomsService {
	List<ChatRoom> getPublicChatRooms();
}
