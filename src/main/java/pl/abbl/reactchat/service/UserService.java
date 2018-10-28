package pl.abbl.reactchat.service;

import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.entity.ChatRoom;
import pl.abbl.reactchat.entity.ChatUser;

import java.util.Map;

public interface UserService {
    AbstractCallback register(Map<String, String> userCredentials);

    ChatUser getUserInformation(String username);

    ChatUser getUserInformation(int userId);
}
