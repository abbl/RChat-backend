package pl.abbl.reactchat.model;

import lombok.Data;
import pl.abbl.reactchat.utils.TokenGenerator;

@Data
public class User {
	private String userToken;
	private String userName;
	
	public User(String userName) {
		this.userToken = TokenGenerator.generateToken(64);
		this.userName = userName;
	}
}
