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
@RequestMapping("api/secure")
public class ChatMessageController {
    @Autowired
    private ChatMessageService chatMessageService;

    @PostMapping("/chatMessages")
    public AbstractCallback postMessageInChatRoom(@RequestBody Map<String, String> message, HttpServletRequest request){
        return chatMessageService.postMessage(message, request);
    }

    @GetMapping("/chatMessages")
    public List<ChatMessage> getChatMessages(@RequestParam("roomId") String roomId, HttpServletRequest request){
        return chatMessageService.findAllByRoomId(Integer.parseInt(roomId), request);
    }

    @GetMapping("/chatMessages/getLastMessageInChatRoom")
    public ChatMessage getLastChatMessageInChatRoom(@RequestParam("roomId") String roomId, HttpServletRequest request){
        return chatMessageService.getLastMessageInChatRoom(Integer.parseInt(roomId));
    }
}
