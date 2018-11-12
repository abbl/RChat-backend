package pl.abbl.reactchat.repositories.impl;

import org.springframework.stereotype.Repository;
import pl.abbl.reactchat.entities.ChatMessage;
import pl.abbl.reactchat.entities.ChatRoom;
import pl.abbl.reactchat.repositories.ChatContentRepository;

import java.util.List;

/**
 * @inheritDoc
 */
@Repository
public class ChatContentRepositoryImpl implements ChatContentRepository {
    private static final int FETCH_RANGE_LIMIT = 15;

    @Override
    public void createChatContentTable(ChatRoom chatRoom) {

    }

    @Override
    public void saveAndFlush(ChatRoom chatRoom, ChatMessage chatMessage) {

    }

    @Override
    public List<ChatMessage> findLastMessagesByRange(ChatRoom chatRoom, int range) {
        if(chatRoom != null && range <= FETCH_RANGE_LIMIT){

        }
        return null;
    }

    @Override
    public List<ChatMessage> findMessagesByIndexRange(ChatRoom chatRoom, int start, int end) {
        return null;
    }
}
