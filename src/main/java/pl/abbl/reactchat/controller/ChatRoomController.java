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
@RequestMapping("api/secure")
public class ChatRoomController {
	@Autowired
	private ChatRoomsService chatRoomsService;

	@GetMapping("/chatroom")
	public List<ChatRoom> getUserChatRooms(HttpServletRequest request){
		return chatRoomsService.getUserChatRooms(request);
	}

	@PostMapping("/chatroom")
	public AbstractCallback addChatRoom(@RequestBody Map<String, String> requestBody, HttpServletRequest request){
		return chatRoomsService.saveChatRoom(requestBody, request);
	}

	@PutMapping("/chatroom")
	public AbstractCallback updateChatRoom(){
		return null;
	}

	@DeleteMapping("/chatroom")
	public AbstractCallback removeChatRoom(){
		return null;
	}


	@GetMapping("/chatroom/public")
	public List<ChatRoom> getPublicChatRooms(){
		return chatRoomsService.getPublicChatRooms();
	}

	@PostMapping("/chatroom/join")
	public AbstractCallback joinChatRoom(@RequestBody Map<String, String> requestBody, HttpServletRequest request){
		return chatRoomsService.joinChatRoom(requestBody, request);
	}

	@PostMapping("/chatroom/invite")
	public AbstractCallback inviteToChatRoom(@RequestBody Map<String, String> requestBody, HttpServletRequest request){
		return chatRoomsService.inviteUser(requestBody, request);
	}
}

