package pl.abbl.reactchat.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class AnotherDummyController {

    @SendTo("/topic/test")
    @MessageMapping(value = "/hello")
    public String hello(String menuItem)  {
        return "test";
    }
}
