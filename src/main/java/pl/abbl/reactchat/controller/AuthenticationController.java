package pl.abbl.reactchat.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.callbacks.AuthenticationCallback;
import pl.abbl.reactchat.service.AuthenticationService;

@CrossOrigin
@RestController
@RequestMapping("api")
public class AuthenticationController {
	@Autowired
	private AuthenticationService authenticationService;
	
}

/*
 * 	@ResponseBody
	@RequestMapping("/register")
	public AbstractCallback createUserAccount(@RequestBody Map<String, String> data) {
		return authenticationService.createUser(data);
	}
	
	@ResponseBody
	@RequestMapping("/authenticate")
	public AbstractCallback authenticate(@RequestBody Map<String, String> data) {
		return authenticationService.createUser(data.get("username"));
	}
	
	@RequestMapping("/logout")
	public void logout(@RequestBody Map<String, String> data) {
		authenticationService.removeUser(data.get("token"));
	}
 */
