package pl.abbl.reactchat.services;

import pl.abbl.reactchat.entities.ChatRoom;

/**
 * This service has a "power" to update users with context changes through WebSockets.
 * User simply has to subscribe to certain path to receive updates.
 */
public interface ContextChangeService {
    void updateUsersOnRoomChange(ChatRoom chatRoom);
}
