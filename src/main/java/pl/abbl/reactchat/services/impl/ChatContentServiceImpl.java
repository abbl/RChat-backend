package pl.abbl.reactchat.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.abbl.reactchat.models.ChatMessage;
import pl.abbl.reactchat.models.ChatRoom;
import pl.abbl.reactchat.repositories.ChatContentRepository;
import pl.abbl.reactchat.services.ChatContentService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatContentServiceImpl implements ChatContentService {
    private ChatContentRepository chatContentRepository;

    public ChatContentServiceImpl(@Autowired ChatContentRepository chatContentRepository){
        this.chatContentRepository = chatContentRepository;

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setId(1);
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setAuthor("Abbl");
        chatMessage.setContent("Test");
        chatMessage.setTime("Testtest");

        saveAndFlush(chatRoom, chatMessage);
        System.out.println(findLastMessagesByRange(chatRoom, 15).toString());
        System.out.println(findMessagesByIndexRange(chatRoom, 1, 2).toString());
    }

    @Override
    public void createChatContentTable(ChatRoom chatRoom) {
        chatContentRepository.createChatContentTable(chatRoom);
    }

    @Override
    public void saveAndFlush(ChatRoom chatRoom, ChatMessage chatMessage) {
        chatContentRepository.saveAndFlush(chatRoom, chatMessage);
    }

    @Override
    public List findLastMessagesByRange(ChatRoom chatRoom, int range) {
        return chatContentRepository.findLastMessagesByRange(chatRoom, range);
    }

    @Override
    public List<ChatMessage> findMessagesByIndexRange(ChatRoom chatRoom, int start, int end) {
        if(start == 0)
            return new ArrayList<>();

        return chatContentRepository.findMessagesByIndexRange(chatRoom, start, end);
    }
}
