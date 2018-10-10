package pl.abbl.reactchat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.callbacks.AuthenticationCallback;
import pl.abbl.reactchat.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("api")
public class AuthenticationController {
	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public AbstractCallback register(@RequestBody Map<String, String> data){
		return userService.register(data);
	}
}
