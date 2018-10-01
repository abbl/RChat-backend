package pl.abbl.reactchat.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pl.abbl.reactchat.model.ChatMessage;
import pl.abbl.reactchat.model.ChatRoom;
import pl.abbl.reactchat.service.ChatRoomsService;

@CrossOrigin
@RestController
@RequestMapping("api")
public class ChatController {
	@Autowired
	private ChatRoomsService chatRoomsService;
	
	@PostConstruct
	public void createDefaultChatRoom() {
		chatRoomsService.addChatRoom(new ChatRoom(0, "#default"));
		chatRoomsService.getChatRoom(0).addMessage("Im a default message generated in run process.");
	}
	
	@ResponseBody
	@RequestMapping("/chat/getChatRoomList")
	public List<ChatRoom> getAllChatRooms() {
		return chatRoomsService.getChatRooms();
	}
	
	@ResponseBody
	@RequestMapping(value = "/chat/chatRoom/getRecentMessages", method = RequestMethod.GET)
	public List<ChatMessage> getRecentChatMessages(@RequestParam("id") long id){
		return chatRoomsService.getRecentChatMessages(id);
	}
	
	@RequestMapping(value = "/chat/chatRoom/addNewMessage", method = RequestMethod.POST)
	public void addNewMessage(@RequestParam("chatRoomId") long id ,@RequestParam("message") String message) {
		chatRoomsService.getChatRoom(id).addMessage(message);
	}
}
