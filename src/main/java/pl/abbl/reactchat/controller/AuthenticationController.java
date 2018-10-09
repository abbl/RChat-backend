package pl.abbl.reactchat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.abbl.reactchat.callbacks.AuthenticationCallback;

@RestController
@RequestMapping("api")
public class AuthenticationController {
	@RequestMapping("/secure/test")
	public AuthenticationCallback testSecure() {
		return new AuthenticationCallback("test");
	}
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
