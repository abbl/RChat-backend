package pl.abbl.reactchat.repository;

import pl.abbl.reactchat.callbacks.AbstractCallback;
import pl.abbl.reactchat.callbacks.AuthenticationCallback;
import pl.abbl.reactchat.model.User;

public interface UserRepository {
	AbstractCallback createUser(String userName);
	void removeUser(String token);
	User getUser(String token);
}
