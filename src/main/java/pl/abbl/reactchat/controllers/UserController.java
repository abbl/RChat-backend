package pl.abbl.reactchat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.abbl.reactchat.models.RoomRight;
import pl.abbl.reactchat.services.UserService;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/secure/user/role")
    public RoomRight getUserRoleInGivenChatRoom(@RequestParam("userId") int userId, @RequestParam("roomId") int roomId){
        return userService.getUserRoleInChatRoom(userId, roomId);
    }
}
