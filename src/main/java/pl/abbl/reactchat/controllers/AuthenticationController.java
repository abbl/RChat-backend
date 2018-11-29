package pl.abbl.reactchat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.definitions.SecurityConstants;
import pl.abbl.reactchat.services.UserService;

import java.util.Map;

@RestController
public class AuthenticationController {
	@Autowired
	private UserService userService;

	@PostMapping(SecurityConstants.SIGN_UP_URL)
	public AbstractCallback register(@RequestBody Map<String, String> data){
		return userService.register(data);
	}

	@RequestMapping("/secure/cookieTest")
	public AbstractCallback cookieTest(){
		return null;
	}
}
