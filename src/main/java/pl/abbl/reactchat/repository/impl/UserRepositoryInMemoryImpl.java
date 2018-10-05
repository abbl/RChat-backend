package pl.abbl.reactchat.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import pl.abbl.reactchat.callbacks.AuthenticationCallback;
import pl.abbl.reactchat.model.User;
import pl.abbl.reactchat.repository.UserRepository;

@Repository
public class UserRepositoryInMemoryImpl implements UserRepository{
	private List<User> users;
	
	public UserRepositoryInMemoryImpl() {
		users = new ArrayList<>();	
	}
	
	@Override
	public AuthenticationCallback createUser(String userName) {
		if(!isUserNameTaken(userName)) {
			System.out.println("Created user:" + userName);
			User user = new User(userName);
			users.add(user);
			
			return new AuthenticationCallback(user.getToken());
		}else {
			return new AuthenticationCallback(AuthenticationCallback.USERNAME_TAKEN);
		}
	}
	
	private boolean isUserNameTaken(String userName) {
		for (User user : users) {
			if(user.getUserName().equals(userName)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void removeUser(String token) {
		System.out.println("Removed user");
		users.remove(getUser(token));
	}

	@Override
	public User getUser(String token) {
		for (User user : users) {
			if(user.getToken().equals(token)) {
				return user;
			}
		}
		return null;
	}
}
