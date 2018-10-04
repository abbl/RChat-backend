package pl.abbl.reactchat.service;

import pl.abbl.reactchat.model.User;

public interface AuthenticationService {
	String createUser(String userName);
	void removeUser(String token);
	boolean verifyToken(String token);
	String getUserName(String token);
}
