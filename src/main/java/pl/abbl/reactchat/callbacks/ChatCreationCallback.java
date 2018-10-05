package pl.abbl.reactchat.callbacks;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ChatCreationCallback extends AbstractCallback{
	@JsonIgnore
	public static final String CHAT_CREATED_SUCCESSFULLY = "CHAT_CREATED_SUCCESSFULLY";
	@JsonIgnore
	public static final String CHAT_NAME_TAKEN = "CHAT_ERROR_CHATNAME_TAKEN";
	
	public ChatCreationCallback(String response) {
		super(response);
	}
}
