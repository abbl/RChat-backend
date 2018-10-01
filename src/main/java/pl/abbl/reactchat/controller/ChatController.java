package pl.abbl.reactchat.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.abbl.reactchat.model.ChatRoom;
import pl.abbl.reactchat.service.ChatRoomsService;

@RestController
@RequestMapping("api")
public class ChatController {
	@Autowired
	private ChatRoomsService chatRoomsService;
	
	@PostConstruct
	public void createDefaultChatRoom() {
		chatRoomsService.addChatRoom(new ChatRoom(0, "#default"));
	}
	
	@RequestMapping("/chatRooms/getAll")
	public List<ChatRoom> getAllChatRooms() {
		return chatRoomsService.getChatRooms();
	}
}
