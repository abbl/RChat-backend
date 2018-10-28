package pl.abbl.reactchat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.callbacks.ChatMessageCallback;
import pl.abbl.reactchat.entity.ChatMessage;
import pl.abbl.reactchat.entity.ChatRoom;
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
    public List<ChatMessage> findAllByRoomId(int roomId, HttpServletRequest request) {
        ChatUser chatUser = userRepository.findByJwtToken(request);

        if(chatUser != null){
            if(chatRoomParticipantsRepository.isUserParticipantOfChatRoom(chatUser.getId(), roomId) != null){
                return chatMessageRepository.findAllByRoomId(roomId);
            }
        }else{
            return null;
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
                    if(chatRoomRepository.isChatRoomPrivate(chatRoomId) != null){
                        if(chatRoomParticipantsRepository.isUserParticipantOfChatRoom(chatUser.getId(), chatRoomId) == null)
                            return new ChatMessageCallback(ChatMessageCallback.NOT_PARTICIPANT_OF_CHAT_ROOM);
                    }
                    ChatMessage chatMessage = new ChatMessage(incrementIndependentMessageId(chatRoomId), +chatRoomId, chatUser.getUsername(), message);
                    chatMessageRepository.saveAndFlush(chatMessage);
                }else{
                    return new ChatMessageCallback(ChatMessageCallback.MISSING_FIELD);
                }
            }catch (NumberFormatException e){
                System.out.println(e);
                return new ChatMessageCallback(ChatMessageCallback.INVALID_CHAT_ROOM_ID);
            }finally {

            }
        }
        return null;
    }

    private int incrementIndependentMessageId(int chatRoomId){
        ChatMessage lastChatRoomMessage = getLastMessageInChatRoom(chatRoomId);

        if(lastChatRoomMessage != null){
            return lastChatRoomMessage.getRoomIndependentMessageId() + 1;
        }
        return 1;
    }

    @Override
    public ChatMessage getLastMessageInChatRoom(int roomId) {
        List<ChatMessage> result = chatMessageRepository.getLastMessageByRoomId(roomId, new PageRequest(0, 1)).getContent();

        if(!result.isEmpty()){
            return result.get(0);
        }
        return null;
    }
}


