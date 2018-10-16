package pl.abbl.reactchat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.entity.ChatMessage;
import pl.abbl.reactchat.repository.ChatMessageRepository;
import pl.abbl.reactchat.service.ChatMessageService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class ChatMessageController {
    @Autowired
    private ChatMessageService chatMessageService;

    @GetMapping("/chatMessages")
    public List<ChatMessage> getChatMessagesFromPublicRoom(@RequestParam("roomId") int roomId){
        return chatMessageService.findAllByRoomId(roomId);
    }

    @PostMapping("/secure/chatMessages")
    public AbstractCallback postMessageInChatRoom(@RequestBody Map<String, String> message, HttpServletRequest request){
        return null;
    }

    @GetMapping("/secure/chatMessages")
    public List<ChatMessage> getChatMessagesFromPrivateRoom(@RequestParam("roomId") int roomId, HttpServletRequest request){
        return chatMessageService.findAllByRoomId(roomId, request);
    }
}
