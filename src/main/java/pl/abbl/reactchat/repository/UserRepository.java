package pl.abbl.reactchat.repository;

import pl.abbl.reactchat.model.User;

public interface UserRepository {
	String createUser(String userName);
	void removeUser(String token);
	User getUser(String token);
}
