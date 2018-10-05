package pl.abbl.reactchat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.callbacks.ChatCreationCallback;
import pl.abbl.reactchat.model.ChatMessage;
import pl.abbl.reactchat.model.ChatRoom;
import pl.abbl.reactchat.repository.ChatRoomsRepository;
import pl.abbl.reactchat.service.ChatRoomsService;

@Service
public class ChatRoomsServiceImpl implements ChatRoomsService{
	@Autowired
	private ChatRoomsRepository chatRoomsRepository;
	
	@Override
	public AbstractCallback addChatRoom(String ownerToken, String roomName, String roomDesc) {
		return chatRoomsRepository.addChatRoom(ownerToken, roomName, roomDesc);
	}

	@Override
	public void removeChatRoom(String ownerToken, String roomName) {
		// TODO 

	}

	
	@Override
	public List<ChatMessage> getRecentChatMessages(long id) {
		return chatRoomsRepository.getRecentChatMessages(id);
	}
	
	@Override
	public List<ChatRoom> getChatRooms() {
		return chatRoomsRepository.getChatRooms();
	}

	@Override
	public void addMessageToRoom(long roomId, String sender, String message) {
		if(sender != null && message != null)
			if(!sender.isEmpty() && !message.isEmpty())
				chatRoomsRepository.sendMessage(roomId, sender, message);	
	}
}
