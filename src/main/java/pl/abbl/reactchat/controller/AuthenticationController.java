package pl.abbl.reactchat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pl.abbl.reactchat.service.AuthenticationService;

@CrossOrigin
@RestController
@RequestMapping("api")
public class AuthenticationController {
	@Autowired
	private AuthenticationService authenticationService;
	
	@ResponseBody
	@RequestMapping("/authenticate")
	public String authenticate(String userName) {
		return authenticationService.createUser(userName);
	}
	
	@RequestMapping("/logout")
	public void logout(String token) {
		//TODO
	}
}
