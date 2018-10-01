package pl.abbl.reactchat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.abbl.reactchat.model.ChatMessage;
import pl.abbl.reactchat.model.ChatRoom;
import pl.abbl.reactchat.repository.ChatRoomsRepository;
import pl.abbl.reactchat.service.ChatRoomsService;

@Service
public class ChatRoomsServiceImpl implements ChatRoomsService{
	@Autowired
	private ChatRoomsRepository chatRoomsRepository;
	
	@Override
	public void addChatRoom(ChatRoom chatRoom) {
		chatRoomsRepository.addChatRoom(chatRoom);
	}

	@Override
	public void removeChatRoom(ChatRoom chatRoom) {
		chatRoomsRepository.removeChatRoom(chatRoom);
	}

	@Override
	public ChatRoom getChatRoom(long id) {
		return chatRoomsRepository.getChatRoom(id);
	}
	
	@Override
	public List<ChatMessage> getRecentChatMessages(long id) {
		return chatRoomsRepository.getRecentChatMessages(id);
	}
	
	@Override
	public List<ChatRoom> getChatRooms() {
		return chatRoomsRepository.getChatRooms();
	}

}
