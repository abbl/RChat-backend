package pl.abbl.reactchat.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.entity.ChatRoom;
import pl.abbl.reactchat.service.ChatRoomsService;


@RestController
@RequestMapping("api")
public class ChatRoomController {
	@Autowired
	private ChatRoomsService chatRoomsService;

	@GetMapping("/chatrooms/public")
	public List<ChatRoom> getPublicChatRooms(){
		return chatRoomsService.getPublicChatRooms();
	}

	@GetMapping("/secure/chatrooms/private")
    public List<ChatRoom> getPrivateChatRooms(){
	    return null; //TODO correct fetching of private chatrooms.
    }

	@PostMapping("/secure/chatrooms")
	public AbstractCallback addPublicChannel(@RequestBody Map<String, String> chatRoom){
		return null;
	}
}

