package pl.abbl.reactchat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.callbacks.AuthenticationCallback;
import pl.abbl.reactchat.definitions.PostParametersConstants;
import pl.abbl.reactchat.entity.ChatUser;
import pl.abbl.reactchat.entity.Role;
import pl.abbl.reactchat.repository.RoleRepository;
import pl.abbl.reactchat.repository.UserRepository;
import pl.abbl.reactchat.service.UserService;

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
                ChatUser chatUser = new ChatUser();
                chatUser.setUsername(username);
                chatUser.setPassword(passwordEncoder.encode(password));
                chatUser.setActive(1);
                usersRepository.saveAndFlush(chatUser);
                Role role = new Role();
                role.setUsername(username);
                role.setAuthority("USER"); //TODO create definition of roles.
                roleRepository.saveAndFlush(role);
                return new AuthenticationCallback(AuthenticationCallback.REGISTER_SUCCESSFUL);
            }else{
                return new AuthenticationCallback(AuthenticationCallback.USERNAME_TAKEN);
            }
        }else{
            return new AuthenticationCallback(AuthenticationCallback.VALUE_OF_FIELD_IS_MISSING);
        }
    }
}
