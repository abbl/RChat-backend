package pl.abbl.reactchat.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.entity.ChatRoom;
import pl.abbl.reactchat.service.ChatRoomsService;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("api")
public class ChatRoomController {
	@Autowired
	private ChatRoomsService chatRoomsService;

	@GetMapping("/chatroom/public")
	public List<ChatRoom> getPublicChatRooms(){
		return chatRoomsService.getPublicChatRooms();
	}

	@GetMapping("/secure/chatroom/private")
    public List<ChatRoom> getPrivateChatRooms(HttpServletRequest request){
	    return chatRoomsService.getPrivateChatRooms(request);
    }

	@PostMapping("/secure/chatroom")
	public AbstractCallback addChatRoom(@RequestBody Map<String, String> requestBody, HttpServletRequest request){
		return chatRoomsService.saveChatRoom(requestBody, request);
	}

	@PostMapping("/secure/chatroom/invite")
	public AbstractCallback inviteToChatRoom(@RequestBody Map<String, String> requestBody, HttpServletRequest request){
		return chatRoomsService.inviteUser(requestBody, request);
	}
}

