package pl.abbl.reactchat.services;

import pl.abbl.reactchat.models.ChatMessage;
import pl.abbl.reactchat.models.ChatRoom;

import java.util.List;

public interface ChatContentService {
    void createChatContentTable(ChatRoom chatRoom);
    void saveAndFlush(ChatRoom chatRoom, ChatMessage chatMessage);
    List findLastMessagesByRange(ChatRoom chatRoom, int range);
    List<ChatMessage> findMessagesByIndexRange(ChatRoom chatRoom, int start, int end);
}
