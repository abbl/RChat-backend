package pl.abbl.reactchat.callbacks;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AuthenticationCallback extends AbstractCallback{
	@JsonIgnore
	public static final String USERNAME_TAKEN = "REGISTER_USERNAME_TAKEN";
	@JsonIgnore
	public static final String PASSWORD_DONT_MATCH_REQ = "REGISTER_PASSWORD_DONT_MATCH_REQUIREMENTS";
	@JsonIgnore
	public static final String EMAIL_TAKEN = "REGISTER_EMAIL_TAKEN";
	@JsonIgnore
	public static final String INVALID_EMAIL_FORMAT = "INVALID_EMAIL_FORMAT";
	@JsonIgnore
	public static final String ACCOUNT_DONT_EXIST = "ACCOUNT_DONT_EXIST";
	@JsonIgnore
	public static final String INVALID_CREDENTIALS = "INVALID_CREDENTIALS";
	
	public AuthenticationCallback(String response) {
		super(response);
	}
}
