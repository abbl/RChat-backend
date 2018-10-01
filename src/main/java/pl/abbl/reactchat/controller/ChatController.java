package pl.abbl.reactchat.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.abbl.reactchat.model.ChatMessage;

@RestController
public class ChatController {
	private static final ChatMessage TEST_MESSAGE = new ChatMessage(1, "Im just a test message");
	
	@RequestMapping("/")
	public ChatMessage index() {
		return TEST_MESSAGE;
	}
}
