package pl.abbl.reactchat.services;

import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.entities.ChatUser;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

public interface UserService {
    AbstractCallback register(Map<String, String> userCredentials);
    ChatUser getUserInformationByUsername(String username);
    ChatUser getUserInformationById(int userId);
    ChatUser getUserInformationByJwt(HttpServletRequest httpServletRequest);
    ChatUser getUserInformationByPrincipal(Principal principal);
}
