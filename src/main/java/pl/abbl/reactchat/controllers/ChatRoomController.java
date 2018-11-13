package pl.abbl.reactchat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.definitions.enums.ChatRoomStatus;
import pl.abbl.reactchat.definitions.enums.ChatRoomType;
import pl.abbl.reactchat.models.ChatRoom;
import pl.abbl.reactchat.services.ChatRoomService;

import java.security.Principal;
import java.util.Map;

@RestController
public class ChatRoomController {
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/secure/chatroom")
    public AbstractCallback createChatRoom(@RequestBody Map<String, Object> requestBody, Principal principal){
        ChatRoom chatRoom = new ChatRoom((String) requestBody.get("name"), (String) requestBody.get("description"), ChatRoomType.valueOf((String) requestBody.get("type")));

        return chatRoomService.createChatRoom(chatRoom, principal);
    }

    @PutMapping("/secure/chatroom")
    public AbstractCallback updateChatRoom(Map<String, Object> json, Principal principal){
        ChatRoom chatRoom = new ChatRoom((int) json.get("id"),(String) json.get("name"), (String) json.get("description"), ChatRoomType.valueOf((String) json.get("type")), ChatRoomStatus.valueOf((String) json.get("status")));

        return chatRoomService.updateChatRoom(chatRoom, principal);
    }

    @MessageMapping("/request/chatroom/list")
    public void subscribeToChatRoomList(Principal principal){
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/topic/chatroom/list", chatRoomService.getUserChatRooms(principal));
    }
}
