package pl.abbl.reactchat.services;

import pl.abbl.reactchat.models.ChatMessage;
import pl.abbl.reactchat.models.ChatRoom;

/**
 * This service has a "power" to update users with context changes through WebSockets.
 * User simply has to subscribe to certain path to receive updates.
 */
public interface ContextChangeService {
    void updateUsersOnRoomChange(ChatRoom chatRoom);
    void updateUsersOnNewMessage(ChatRoom chatRoom, ChatMessage chatMessage);
}
