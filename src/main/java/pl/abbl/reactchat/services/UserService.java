package pl.abbl.reactchat.services;

import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.models.ChatUser;
import pl.abbl.reactchat.models.RoomRight;

import java.security.Principal;
import java.util.Map;

public interface UserService {
    AbstractCallback register(Map<String, String> userCredentials);
    ChatUser getUserInformationByUsername(String username);
    ChatUser getUserInformationById(int userId);
    ChatUser getUserInformationByPrincipal(Principal principal);
    RoomRight getUserRoleInChatRoom(Principal principal, int roomId);
}
