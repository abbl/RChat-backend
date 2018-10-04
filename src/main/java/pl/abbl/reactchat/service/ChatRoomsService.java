package pl.abbl.reactchat.service;

import java.util.List;

import org.springframework.stereotype.Service;

import pl.abbl.reactchat.model.ChatMessage;
import pl.abbl.reactchat.model.ChatRoom;

public interface ChatRoomsService {
	String addChatRoom(String token, String roomName, String roomDesc);
	void removeChatRoom(String token, String roomName);
	void addMessageToRoom(long roomId, String sender, String message);
	List<ChatMessage> getRecentChatMessages(long id);
	List<ChatRoom> getChatRooms();
}
