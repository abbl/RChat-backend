package pl.abbl.reactchat.service;

import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.entity.ChatRoom;
import pl.abbl.reactchat.entity.ChatUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface UserService {
    AbstractCallback register(Map<String, String> userCredentials);
    ChatUser getUserInformationByUsername(String username);
    ChatUser getUserInformationById(int userId);
    ChatUser getUserInformationByJwt(HttpServletRequest httpServletRequest);
}
