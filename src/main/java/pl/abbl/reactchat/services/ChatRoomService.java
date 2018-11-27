package pl.abbl.reactchat.services;

import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.models.ChatRoom;
import java.security.Principal;
import java.util.List;

public interface ChatRoomService {
    AbstractCallback createChatRoom(ChatRoom chatRoom, Principal principal);
    AbstractCallback updateChatRoom(ChatRoom chatRoom, Principal principal);
    AbstractCallback joinChatRoom(ChatRoom chatRoom, Principal principal);
    List<ChatRoom> getUserChatRooms(Principal principal);
    List<ChatRoom> getPublicChatRooms();
    List<ChatRoom> getChatRoomByListOfId(List<Integer> idList);
    ChatRoom getChatRoomById(int id);
}
