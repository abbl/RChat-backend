package pl.abbl.reactchat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.callbacks.AuthenticationCallback;

import java.security.Principal;

/**
 * Thing made purely to test out sockets.
 */
@RestController
public class DummyController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/testtest")
    public void testMe(Principal principal){
        AbstractCallback abstractCallback = new AuthenticationCallback(AuthenticationCallback.REGISTER_SUCCESSFUL);
        simpMessagingTemplate.convertAndSendToUser("Test", "/queue/test", "This message is only for Test");

        System.out.println("I was called by user:" + principal.getName());
    }
}
