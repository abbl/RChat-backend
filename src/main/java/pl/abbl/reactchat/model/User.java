package pl.abbl.reactchat.model;

import pl.abbl.reactchat.utils.TokenGenerator;

public class User {
	private String userToken;
	private String userName;
	
	public User(String userName) {
		this.userToken = TokenGenerator.generateToken(64);
		this.userName = userName;
	}
	
	public String getToken() {
		return userToken;
	}
	
	public String getUserName() {
		return userName;
	}
	
}
