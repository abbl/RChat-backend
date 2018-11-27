package pl.abbl.reactchat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.models.ChatRoom;
import pl.abbl.reactchat.services.ChatRoomService;

import java.security.Principal;
import java.util.List;

@RestController
public class ChatRoomController {
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/secure/chatroom")
    public AbstractCallback createChatRoom(@RequestBody ChatRoom chatRoom, Principal principal){
        return chatRoomService.createChatRoom(chatRoom, principal);
    }

    @PutMapping("/secure/chatroom")
    public AbstractCallback updateChatRoom(@RequestBody ChatRoom chatRoom, Principal principal){
        return chatRoomService.updateChatRoom(chatRoom, principal);
    }

    @GetMapping("/secure/chatroom")
    public List<ChatRoom> getPublicChatRoomList(){
        return chatRoomService.getPublicChatRooms();
    }

    @PostMapping("/secure/chatroom/join")
    public AbstractCallback joinChatRoom(@RequestBody ChatRoom chatRoom, Principal principal){
        return chatRoomService.joinChatRoom(chatRoom, principal);
    }

    @MessageMapping("/request/chatroom/list")
    public void getUserChatRoomList(Principal principal){
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/topic/chatroom/list", chatRoomService.getUserChatRooms(principal));
    }
}
