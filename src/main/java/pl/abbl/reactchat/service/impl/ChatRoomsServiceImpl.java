package pl.abbl.reactchat.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.callbacks.ChatCreationCallback;
import pl.abbl.reactchat.model.ChatMessage;
import pl.abbl.reactchat.model.ChatRoom;
import pl.abbl.reactchat.repository.ChatRoomsRepository;
import pl.abbl.reactchat.repository.enums.ChatRoomType;
import pl.abbl.reactchat.service.ChatRoomsService;

@Service
public class ChatRoomsServiceImpl implements ChatRoomsService{
	@Autowired
	private ChatRoomsRepository chatRoomsRepository;


	@Override
	public List<ChatRoom> getPublicChatRooms() {
		List<ChatRoom> chatRooms = chatRoomsRepository.findAll();
		List<ChatRoom> publicChatRooms = new ArrayList<>();
		System.out.println("Size:" + chatRooms.size());
		for(ChatRoom chatRoom : chatRooms){
			System.out.println("ChatRoom " + chatRoom.getName() + "GetType:" + chatRoom.getType());
			if(chatRoom.getType() == ChatRoomType.PUBLIC){
				publicChatRooms.add(chatRoom);
			}
		}

		return publicChatRooms;
	}
}
