package pl.abbl.reactchat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.abbl.reactchat.callbacks.ChatCallbacks;
import pl.abbl.reactchat.model.ChatMessage;
import pl.abbl.reactchat.model.ChatRoom;
import pl.abbl.reactchat.repository.ChatRoomsRepository;
import pl.abbl.reactchat.service.ChatRoomsService;

@Service
public class ChatRoomsServiceImpl implements ChatRoomsService{
	@Autowired
	private ChatRoomsRepository chatRoomsRepository;
	
	@Override
	public String addChatRoom(String ownerToken, String roomName, String roomDesc) {
		if(chatRoomsRepository.addChatRoom(ownerToken, roomName, roomDesc))
			return ChatCallbacks.CHAT_CREATED_SUCCESSFULLY;
		return ChatCallbacks.CHAT_NAME_TAKEN;
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
