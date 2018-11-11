package pl.abbl.reactchat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.definitions.enums.ChatRoomType;
import pl.abbl.reactchat.entities.ChatRoom;
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
        ChatRoom chatRoom = new ChatRoom((String) json.get("name"), (String) json.get("description"), (ChatRoomType) json.get("type"));

        return chatRoomService.updateChatRoom(chatRoom, principal);
    }

    @MessageMapping("/queue/context/roomlist")
    public void subscribeToChatRoomList(Principal principal){
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/queue/context/roomlist", chatRoomService.getUserChatRooms(principal));
    }
}
