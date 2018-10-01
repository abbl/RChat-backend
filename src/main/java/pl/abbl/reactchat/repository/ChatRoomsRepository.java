package pl.abbl.reactchat.repository;

import java.util.List;

import pl.abbl.reactchat.model.ChatMessage;
import pl.abbl.reactchat.model.ChatRoom;

public interface ChatRoomsRepository {
	void addChatRoom(ChatRoom chatRoom);
	void removeChatRoom(ChatRoom chatRoom);
	ChatRoom getChatRoom(long id);
	List<ChatMessage> getRecentChatMessages(long id);
	List<ChatRoom> getChatRooms();
}
