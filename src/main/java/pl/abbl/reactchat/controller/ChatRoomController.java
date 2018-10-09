package pl.abbl.reactchat.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pl.abbl.reactchat.model.ChatRoom;
import pl.abbl.reactchat.service.ChatRoomsService;


@RestController
@RequestMapping("api")
public class ChatRoomController {
	@Autowired
	private ChatRoomsService chatRoomsService;

	@PostConstruct
	public void createDefaultChatRoom() {

	}
	
	@GetMapping("/chatrooms/public")
	public List<ChatRoom> getPublicChatRooms(){
		return chatRoomsService.getPublicChatRooms();
	}

	@PostMapping("/chatrooms/private")
    public List<ChatRoom> getPrivateChatRooms(){
	    return null; //TODO correct fetching of private chatrooms.
    }
}

