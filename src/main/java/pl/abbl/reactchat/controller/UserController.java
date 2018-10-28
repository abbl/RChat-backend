package pl.abbl.reactchat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.abbl.reactchat.entity.ChatUser;
import pl.abbl.reactchat.service.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/secure")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ChatUser getUserInformation(@Param("username") String username){
        return userService.getUserInformationByUsername(username);
    }

    @GetMapping("/loggedUser")
    public ChatUser getUserInformation(HttpServletRequest httpServletRequest){
        return userService.getUserInformationByJwt(httpServletRequest);
    }
}
