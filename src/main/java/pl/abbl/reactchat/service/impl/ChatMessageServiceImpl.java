package pl.abbl.reactchat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.entity.ChatMessage;
import pl.abbl.reactchat.entity.ChatUser;
import pl.abbl.reactchat.repository.ChatMessageRepository;
import pl.abbl.reactchat.repository.ChatRoomParticipantsRepository;
import pl.abbl.reactchat.repository.ChatRoomRepository;
import pl.abbl.reactchat.repository.UserRepository;
import pl.abbl.reactchat.service.ChatMessageService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRoomParticipantsRepository chatRoomParticipantsRepository;

    @Override
    public List<ChatMessage> findAllByRoomId(int roomId) {
        if(chatRoomRepository.isChatRoomPrivate(roomId) == null){
            return chatMessageRepository.findAllByRoomId(roomId);
        }
        return null;
    }

    @Override
    public List<ChatMessage> findAllByRoomId(int roomId, HttpServletRequest request) {
        if(chatRoomRepository.isChatRoomPrivate(roomId) != null){
            ChatUser chatUser = userRepository.findByJwtToken(request);

            if(chatUser != null){
                if(chatRoomParticipantsRepository.isUserParticipantOfChatRoom(chatUser.getId(), roomId) != null){
                    return chatMessageRepository.findAllByRoomId(roomId);
                }
            }else{
                return null;
            }
        }else{
            return findAllByRoomId(roomId);
        }
        return null;
    }

    @Override
    public AbstractCallback postMessage(Map<String, String> chatMessage, HttpServletRequest request) {

    }
}
