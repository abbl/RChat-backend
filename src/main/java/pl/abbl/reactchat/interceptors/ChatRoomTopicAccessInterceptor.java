package pl.abbl.reactchat.interceptors;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import pl.abbl.reactchat.definitions.enums.RoomRightLevel;
import pl.abbl.reactchat.services.RoomRightService;
import pl.abbl.reactchat.services.UserService;

import java.util.Objects;

public class ChatRoomTopicAccessInterceptor implements ChannelInterceptor {
    private static final String CHAT_ROOM_SUBSCRIBE_URL = "/user/topic/chatroom/";
    private RoomRightService roomRightService;
    private UserService userService;

    public ChatRoomTopicAccessInterceptor(RoomRightService roomRightService, UserService userService){
        this.roomRightService = roomRightService;
        this.userService = userService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);

        if(StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand())){
            if(Objects.requireNonNull(headerAccessor.getDestination()).startsWith(CHAT_ROOM_SUBSCRIBE_URL)){
                int roomId = Integer.parseInt(headerAccessor.getDestination().replace(CHAT_ROOM_SUBSCRIBE_URL, ""));
                int userId = userService.getUserInformationByPrincipal(headerAccessor.getUser()).getId();

                if(checkIfUserIsParticipantOfThisRoom(userId, roomId)){
                    return message;
                }
                throw new MessagingException("User isn't participant of room: " + roomId);
            }
        }
        return message;
    }

    private boolean checkIfUserIsParticipantOfThisRoom(int userId, int roomId){
        return roomRightService.isUserRightLevelHighEnough(userId, roomId, RoomRightLevel.PARTICIPANT);
    }
}
