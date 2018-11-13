package pl.abbl.reactchat.repositories;

import pl.abbl.reactchat.models.ChatUser;

import javax.servlet.http.HttpServletRequest;

public interface UserRepositoryCustom {
    ChatUser findByJwtToken(HttpServletRequest request);
}
