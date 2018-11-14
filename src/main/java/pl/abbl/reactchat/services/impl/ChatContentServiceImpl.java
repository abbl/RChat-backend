package pl.abbl.reactchat.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import pl.abbl.reactchat.models.ChatMessage;
import pl.abbl.reactchat.models.ChatRoom;
import pl.abbl.reactchat.repositories.ChatContentRepository;
import pl.abbl.reactchat.services.ChatContentService;

import java.util.List;

public class ChatContentServiceImpl implements ChatContentService {
    private ChatContentRepository chatContentRepository;

    @Override
    public void createChatContentTable(ChatRoom chatRoom) {

    }

    @Override
    public void saveAndFlush(ChatRoom chatRoom, ChatMessage chatMessage) {

    }

    @Override
    public List findLastMessagesByRange(ChatRoom chatRoom, int range) {
        return null;
    }

    @Override
    public List<ChatMessage> findMessagesByIndexRange(ChatRoom chatRoom, int start, int end) {
        return null;
    }
}
