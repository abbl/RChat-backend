package pl.abbl.reactchat.repositories;

import pl.abbl.reactchat.entities.*;

import java.util.List;

/**
 * Each {@link ChatMessage} is stored in certain table related to {@link ChatRoom}
 * (when new room is created there should be also separate table for {@link ChatMessage} 'entities'),
 * and purpose of this repository is to persist It's content.
 *
 * (Its kinda self explanatory by the name of this class but I wanted to clarify that so you
 * wouldn't think that this repository is persisting all {@link ChatMessage} in one table,
 * Its actually opposite to that)
 *
 * More explanation should be written in Service using this @Repository, at least I think it will be written.
 */
public interface ChatContentRepository {
    void createChatContentTable(ChatRoom chatRoom);
    void saveAndFlush(ChatRoom chatRoom, ChatMessage chatMessage);
    List<ChatMessage> findLastMessagesByRange(ChatRoom chatRoom, int range);
    List<ChatMessage> findMessagesByIndexRange(ChatRoom chatRoom, int start, int end);
}
