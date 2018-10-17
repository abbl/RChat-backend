package pl.abbl.reactchat.callbacks;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ChatRoomCallback extends AbstractCallback{
	@JsonIgnore
	public static final String CHAT_ROOM_CREATED_SUCCESSFULLY = "CHAT_ROOM_CREATED_SUCCESSFULLY";
	@JsonIgnore
	public static final String CHAT_ROOM_NAME_TAKEN = "CHAT_ROOM_NAME_TAKEN";
	@JsonIgnore
	public static final String INVALID_CHAT_ROOM_TYPE = "INVALID_CHAT_ROOM_TYPE";
	@JsonIgnore
	public static final String MISSING_CHAT_ROOM_CREDENTIALS = "MISSING_CHAT_ROOM_CREDENTIALS";
	@JsonIgnore
	public static final String MISSING_OWNER_RIGHTS = "MISSING_OWNER_RIGHTS";
	@JsonIgnore
	public static final String NO_SUCH_USER_FOUND = "NO_SUCH_USER_FOUND";
	@JsonIgnore
	public static final String CANT_INVITE_TO_PUBLIC_ROOM = "CANT_INVITE_TO_PUBLIC_ROOM";

	public ChatRoomCallback(String response) {
		super(response);
	}
}
