package pl.abbl.reactchat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.abbl.reactchat.models.ChatRoom;
import pl.abbl.reactchat.models.ChatUser;
import pl.abbl.reactchat.models.RoomRight;
import pl.abbl.reactchat.services.UserService;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/secure/user/role")
    public RoomRight getUserRoleInGivenChatRoom(@RequestBody ChatUser chatUser, @RequestBody ChatRoom chatRoom){
        return userService.getUserRoleInChatRoom(chatUser, chatRoom);
    }
}
