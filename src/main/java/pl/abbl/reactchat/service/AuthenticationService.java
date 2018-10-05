package pl.abbl.reactchat.service;

import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.callbacks.AuthenticationCallback;

public interface AuthenticationService {
	AbstractCallback createUser(String userName);
	void removeUser(String token);
	boolean verifyToken(String token);
	String getUserName(String token);
}
