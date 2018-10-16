package pl.abbl.reactchat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.callbacks.ChatMessageCallback;
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

import static pl.abbl.reactchat.definitions.PostParametersConstants.CHAT_ROOM_MESSAGE_CHAT_ROOM_ID;
import static pl.abbl.reactchat.definitions.PostParametersConstants.CHAT_ROOM_MESSAGE_MESSAGE;

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
    public AbstractCallback postMessage(Map<String, String> requestBody, HttpServletRequest request) {
        ChatUser chatUser = userRepository.findByJwtToken(request);

        if(chatUser != null){
            try{
                int chatRoomId = Integer.parseInt(requestBody.get(CHAT_ROOM_MESSAGE_CHAT_ROOM_ID));
                String message = requestBody.get(CHAT_ROOM_MESSAGE_MESSAGE);

                if(message != null){
                    if(chatRoomRepository.isChatRoomPrivate(chatRoomId) != null)
                        if(chatRoomParticipantsRepository.isUserParticipantOfChatRoom(chatUser.getId(), chatRoomId) == null)
                            return new ChatMessageCallback(ChatMessageCallback.NOT_PARTICIPANT_OF_CHAT_ROOM);

                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.setRoomId(chatRoomId);
                    chatMessage.setSender(chatUser.getUsername());
                    chatMessage.setMessage(message);
                    chatMessage.setTimeStamp();

                    chatMessageRepository.saveAndFlush(chatMessage);
                }
            }catch (NumberFormatException e){
                return new ChatMessageCallback(ChatMessageCallback.INVALID_CHAT_ROOM_ID);
            }
        }
        return null;
    }
}


