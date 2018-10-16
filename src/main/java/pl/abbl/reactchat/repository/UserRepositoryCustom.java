package pl.abbl.reactchat.repository;

import pl.abbl.reactchat.entity.ChatUser;

import javax.servlet.http.HttpServletRequest;

public interface UserRepositoryCustom {
    ChatUser findByJwtToken(HttpServletRequest request);
}
