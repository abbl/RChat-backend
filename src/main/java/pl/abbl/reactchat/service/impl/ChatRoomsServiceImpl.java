package pl.abbl.reactchat.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.abbl.reactchat.entity.ChatRoom;
import pl.abbl.reactchat.repository.ChatRoomsRepository;
import pl.abbl.reactchat.repository.enums.ChatRoomType;
import pl.abbl.reactchat.service.ChatRoomsService;

@Service
public class ChatRoomsServiceImpl implements ChatRoomsService{
	@Autowired
	private ChatRoomsRepository chatRoomsRepository;


	@Override
	public List<ChatRoom> getPublicChatRooms() {
		List<ChatRoom> publicChatRooms = new ArrayList<>();

		for(ChatRoom chatRoom : chatRoomsRepository.findAll()){
			if(chatRoom.getType() == ChatRoomType.PUBLIC){
				publicChatRooms.add(chatRoom);
			}
		}

		return publicChatRooms;
	}
}
