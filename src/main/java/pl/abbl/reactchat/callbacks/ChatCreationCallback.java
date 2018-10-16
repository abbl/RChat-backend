package pl.abbl.reactchat.callbacks;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ChatCreationCallback extends AbstractCallback{
	@JsonIgnore
	public static final String CHAT_ROOM_CREATED_SUCCESSFULLY = "CHAT_ROOM_CREATED_SUCCESSFULLY";
	@JsonIgnore
	public static final String CHAT_ROOM_NAME_TAKEN = "CHAT_ROOM_NAME_TAKEN";
	@JsonIgnore
	public static final String INVALID_CHAT_ROOM_TYPE = "INVALID_CHAT_ROOM_TYPE";
	@JsonIgnore
	public static final String MISSING_CHAT_ROOM_CREDENTIALS = "MISSING_CHAT_ROOM_CREDENTIALS";

	public ChatCreationCallback(String response) {
		super(response);
	}
}
