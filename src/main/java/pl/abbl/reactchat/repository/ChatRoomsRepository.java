package pl.abbl.reactchat.repository;

import java.util.List;

import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.model.ChatMessage;
import pl.abbl.reactchat.model.ChatRoom;

public interface ChatRoomsRepository {
	AbstractCallback addChatRoom(String ownerToken, String roomName, String roomDesc);
	void removeChatRoom(ChatRoom chatRoom);
	ChatRoom getChatRoom(long id);
	void sendMessage(long roomId, String sender, String message);
	List<ChatMessage> getRecentChatMessages(long id);
	List<ChatRoom> getChatRooms();
}
