package pl.abbl.reactchat.service;

import java.util.List;

import org.springframework.stereotype.Service;

import pl.abbl.reactchat.model.ChatMessage;
import pl.abbl.reactchat.model.ChatRoom;

public interface ChatRoomsService {
	void addChatRoom(ChatRoom chatRoom);
	void removeChatRoom(ChatRoom chatRoom);
	ChatRoom getChatRoom(long id);
	List<ChatMessage> getRecentChatMessages(long id);
	List<ChatRoom> getChatRooms();
}
