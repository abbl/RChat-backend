package pl.abbl.reactchat.services;

import pl.abbl.reactchat.models.ChatMessage;

import java.security.Principal;
import java.util.List;

public interface ChatContentService {
    void saveChatMessage(int chatRoomId, ChatMessage chatMessage, Principal principal);
    List findLastMessagesByRange(int chatRoomId, int range);
    List<ChatMessage> findMessagesByIndexRange(int chatRoomId, int start, int end);
}
