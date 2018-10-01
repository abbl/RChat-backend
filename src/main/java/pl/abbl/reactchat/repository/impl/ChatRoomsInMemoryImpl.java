package pl.abbl.reactchat.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import pl.abbl.reactchat.model.ChatMessage;
import pl.abbl.reactchat.model.ChatRoom;
import pl.abbl.reactchat.repository.ChatRoomsRepository;

@Repository
public class ChatRoomsInMemoryImpl implements ChatRoomsRepository{
	private List<ChatRoom> chatRooms;
	private static final short RECENT_MESSAGES_RANGE = 25;
	
	public ChatRoomsInMemoryImpl() {
		chatRooms = new ArrayList<>();
	}
	
	@Override
	public void addChatRoom(ChatRoom chatRoom) {
		chatRooms.add(chatRoom);
	}

	@Override
	public void removeChatRoom(ChatRoom chatRoom) {
		chatRooms.remove(chatRoom);
	}

	@Override
	public List<ChatRoom> getChatRooms() {
		return chatRooms;
	}
	
	@Override
	public List<ChatMessage> getRecentChatMessages(long id) {
		List<ChatMessage> chatMessages = getChatRoom(id).getMessages();
		return chatMessages.subList(Math.max(0, chatMessages.size() - RECENT_MESSAGES_RANGE), chatMessages.size());
	}
	
	@Override
	public ChatRoom getChatRoom(long id) {
		for (ChatRoom chatRoom : chatRooms) {
			if(chatRoom.getId() == id)
				return chatRoom;
		}
		return null;
	}

}
