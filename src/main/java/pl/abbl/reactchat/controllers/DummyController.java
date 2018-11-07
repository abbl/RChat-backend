package pl.abbl.reactchat.controllers;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.callbacks.AuthenticationCallback;

/**
 * Thing made purely to test out sockets.
 */
@RestController
public class DummyController {

    @RequestMapping("/secure/cookieTest")
    public AbstractCallback cookieTest(){
        return null;
    }

    @RequestMapping("/secure/app")
    @SendTo("/secure/topic")
    public AbstractCallback testMe(){
        return new AuthenticationCallback(AuthenticationCallback.REGISTER_SUCCESSFUL);
    }
}
