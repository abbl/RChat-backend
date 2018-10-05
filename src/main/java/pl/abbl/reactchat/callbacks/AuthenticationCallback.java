package pl.abbl.reactchat.callbacks;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AuthenticationCallback extends AbstractCallback{
	@JsonIgnore
	public static final String USERNAME_TAKEN = "AUTH_ERROR_USERNAME_TAKEN";
	
	public AuthenticationCallback(String response) {
		super(response);
	}
}
