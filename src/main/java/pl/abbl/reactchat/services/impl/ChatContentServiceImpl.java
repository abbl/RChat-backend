package pl.abbl.reactchat.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.abbl.reactchat.definitions.enums.RoomRightLevel;
import pl.abbl.reactchat.models.ChatMessage;
import pl.abbl.reactchat.models.ChatRoom;
import pl.abbl.reactchat.models.ChatUser;
import pl.abbl.reactchat.repositories.ChatContentRepository;
import pl.abbl.reactchat.repositories.parameters.RangeParameter;
import pl.abbl.reactchat.services.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatContentServiceImpl implements ChatContentService {
    @Autowired
    private ChatContentRepository chatContentRepository;
    @Autowired
    private ChatRoomService chatRoomService;
    @Autowired
    private RoomRightService rightService;
    @Autowired
    private UserService userService;
    @Autowired
    private ContextChangeService contextChangeService;

    @Override
    public void saveChatMessage(int chatRoomId, ChatMessage chatMessage, Principal principal) {
        ChatUser chatUser = userService.getUserInformationByPrincipal(principal);
        ChatRoom chatRoom = chatRoomService.getChatRoomById(chatRoomId);

        if(chatUser != null && chatRoom != null){
            if(rightService.isUserRightLevelHighEnough(chatUser.getId(), chatRoom.getId(), RoomRightLevel.PARTICIPANT)){
                if(!chatMessage.getContent().isEmpty()){
                    chatMessage.setAuthor(chatUser.getUsername());
                    chatContentRepository.saveAndFlush(chatRoom, chatMessage);

                    ChatMessage savedMessage = chatContentRepository.getLastMessage(chatRoom);
                    contextChangeService.updateUsersOnNewMessage(chatRoom, savedMessage);
                }
            }
        }

    }

    @Override
    public List findLastMessagesByRange(int chatRoomId, int range) {
        return chatContentRepository.getMessagesByAmount(chatRoomService.getChatRoomById(chatRoomId), range);
    }

    @Override
    public List<ChatMessage> findMessagesByIndexRange(int chatRoomId, RangeParameter rangeParameter) {
        if(rangeParameter.getStart() == 0) //There shouldn't be a message with id 0.
            return new ArrayList<>();

        return chatContentRepository.findMessagesByIndexRange(chatRoomService.getChatRoomById(chatRoomId), rangeParameter);
    }
}
