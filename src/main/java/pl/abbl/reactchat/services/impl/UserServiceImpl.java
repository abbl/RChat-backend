package pl.abbl.reactchat.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.callbacks.AuthenticationCallback;
import pl.abbl.reactchat.definitions.PostParametersConstants;
import pl.abbl.reactchat.entities.ChatUser;
import pl.abbl.reactchat.entities.Role;
import pl.abbl.reactchat.repositories.RoleRepository;
import pl.abbl.reactchat.repositories.UserRepository;
import pl.abbl.reactchat.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository usersRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public AbstractCallback register(Map<String, String> userCredentials) {
        String username = userCredentials.get(PostParametersConstants.USER_NAME);
        String password = userCredentials.get(PostParametersConstants.USER_PASSWORD);

        if(username != null && password != null){
            if(usersRepository.findByUsername(username) == null){
                ChatUser chatUser = new ChatUser(username, passwordEncoder.encode(password), 1);
                Role role = new Role(username, "USER");

                usersRepository.saveAndFlush(chatUser);
                roleRepository.saveAndFlush(role);
                return new AuthenticationCallback(AuthenticationCallback.REGISTER_SUCCESSFUL);
            }else{
                return new AuthenticationCallback(AuthenticationCallback.USERNAME_TAKEN);
            }
        }else{
            return new AuthenticationCallback(AuthenticationCallback.VALUE_OF_FIELD_IS_MISSING);
        }
    }

    @Override
    public ChatUser getUserInformationByUsername(String username){
        if(username != null) {
            ChatUser chatUser = usersRepository.findByUsername(username);
            if(chatUser != null){
                return getUserInformationById(chatUser.getId());
            }
        }
        return null;
    }

    @Override
    public ChatUser getUserInformationByJwt(HttpServletRequest httpServletRequest) {
        return getUserInformationById(usersRepository.findByJwtToken(httpServletRequest).getId());
    }

    @Override
    public ChatUser getUserInformationById(int userId) {
        ChatUser chatUser = usersRepository.findByIdInt(userId);
        if(chatUser != null){
            ChatUser limitedChatUser = new ChatUser();
            limitedChatUser.setId(chatUser.getId());
            limitedChatUser.setUsername(chatUser.getUsername());
            limitedChatUser.setDescription(chatUser.getDescription());
            return limitedChatUser;
        }
        return null;
    }
}
