package pl.abbl.reactchat.service;

import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.entity.ChatMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ChatMessageService {
    List<ChatMessage> findAllByRoomId(int roomId);
    List<ChatMessage> findAllByRoomId(int roomId, HttpServletRequest request);
    AbstractCallback postMessage(Map<String, String> chatMessage, HttpServletRequest request);
}
