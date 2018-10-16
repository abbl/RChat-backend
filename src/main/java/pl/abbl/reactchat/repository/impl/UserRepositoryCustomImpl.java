package pl.abbl.reactchat.repository.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import pl.abbl.reactchat.entity.ChatUser;
import pl.abbl.reactchat.repository.UserRepository;
import pl.abbl.reactchat.repository.UserRepositoryCustom;

import javax.servlet.http.HttpServletRequest;

import static pl.abbl.reactchat.definitions.SecurityConstants.HEADER_STRING;
import static pl.abbl.reactchat.definitions.SecurityConstants.SECRET;
import static pl.abbl.reactchat.definitions.SecurityConstants.TOKEN_PREFIX;

@Component
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    @Autowired
    private UserRepository userRepository;


    @Override
    public ChatUser findByJwtToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if(token != null){
            String username =
                     JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();
            if(username != null){
                return userRepository.findByUsername(username);
            }
        }
        return null;
    }
}
