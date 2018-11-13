package pl.abbl.reactchat.repositories.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import pl.abbl.reactchat.models.ChatUser;
import pl.abbl.reactchat.repositories.UserRepository;
import pl.abbl.reactchat.repositories.UserRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import static pl.abbl.reactchat.definitions.SecurityConstants.*;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    @Lazy
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
