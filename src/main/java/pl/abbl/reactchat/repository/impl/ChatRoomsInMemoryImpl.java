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
	public boolean addChatRoom(String token, String roomName, String roomDesc) {
		if(getChatRoomByName(roomName) != null) {
			
			return true;
		}
		return false;
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
	
	private ChatRoom getChatRoomByName(String chatRoomName) {
		for (ChatRoom chatRoom : chatRooms) {
			if(chatRoom.getName().equalsIgnoreCase(chatRoomName)){
				return chatRoom;
			}
		}
		return null;
	}

	@Override
	public void sendMessage(long roomId, String sender, String message) {
		ChatRoom target = getChatRoom(roomId);
		
		if(target != null) 
			target.addMessage(sender, message);
	}
}
