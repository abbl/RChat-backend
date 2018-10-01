package pl.abbl.reactchat.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import pl.abbl.reactchat.model.ChatRoom;
import pl.abbl.reactchat.repository.ChatRoomsRepository;

@Repository
public class ChatRoomsInMemoryImpl implements ChatRoomsRepository{
	private List<ChatRoom> chatRooms;
	
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
	public ChatRoom getChatRoom(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
