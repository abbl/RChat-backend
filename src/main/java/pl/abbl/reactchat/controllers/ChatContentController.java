package pl.abbl.reactchat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.abbl.reactchat.configs.ReactChatConfiguration;
import pl.abbl.reactchat.models.ChatMessage;
import pl.abbl.reactchat.services.ChatContentService;

import java.security.Principal;
import java.util.Map;

@RestController
public class ChatContentController {
    @Autowired
    private ChatContentService chatContentService;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/request/chatroom/{chatRoomId}")
    public void getLastMessages(@DestinationVariable int chatRoomId, Principal principal){
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/topic/chatroom/" + chatRoomId,
                chatContentService.findLastMessagesByRange(chatRoomId, ReactChatConfiguration.FETCH_RANGE_LIMIT));
    }

    @MessageMapping("/request/chatroom/{chatRoomId}/range")
    public void getMessagesByIdRange(@DestinationVariable int chatRoomId, @RequestBody Map<String, Object> requestBody, Principal principal){
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/topic/chatroom/" + chatRoomId,
                chatContentService.findMessagesByIndexRange(chatRoomId, (int) requestBody.get("start"), (int) requestBody.get("end")));
    }

    @MessageMapping("/request/chatroom/{chatRoomId}/send")
    public void receiveChatMessage(@DestinationVariable int chatRoomId, @RequestBody ChatMessage chatMessage, Principal principal){
        chatContentService.saveChatMessage(chatRoomId, chatMessage, principal);
    }
}
