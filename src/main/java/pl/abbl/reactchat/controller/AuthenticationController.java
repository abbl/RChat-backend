package pl.abbl.reactchat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.context.request.WebRequest;
import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.callbacks.AuthenticationCallback;
import pl.abbl.reactchat.service.UserService;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
