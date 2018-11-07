package pl.abbl.reactchat.repositories;

import pl.abbl.reactchat.entities.ChatUser;

import javax.servlet.http.HttpServletRequest;

public interface UserRepositoryCustom {
    ChatUser findByJwtToken(HttpServletRequest request);
}
