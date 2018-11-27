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
	public static final String INSUFFICIENT_RIGHTS = "INSUFFICIENT_RIGHTS";
	@JsonIgnore
	public static final String NO_SUCH_USER_FOUND = "NO_SUCH_USER_FOUND";
	@JsonIgnore
	public static final String CANT_INVITE_TO_PUBLIC_ROOM = "CANT_INVITE_TO_PUBLIC_ROOM";
	@JsonIgnore
	public static final String YOU_ARE_ALREADY_MEMBER_OF_THIS_CHANNEL = "YOU_ARE_ALREADY_MEMBER_OF_THIS_CHANNEL";
	@JsonIgnore
	public static final String SUCCESSFULLY_JOINED_CHAT_ROOM = "SUCCESSFULLY_JOINED_CHAT_ROOM";
	@JsonIgnore
	public static final String NO_SUCH_CHAT_ROOM_FIND = "NO_SUCH_CHAT_ROOM_FIND";
	@JsonIgnore
	public static final String CANT_UPDATE_CHAT_ROOM_THAT_DOESNT_EXIST = "CANT_UPDATE_CHAT_ROOM_THAT_DOESNT_EXISTS";
	@JsonIgnore
	public static final String CHAT_ROOM_UPDATED_SUCCESSFULLY = "CHAT_ROOM_UPDATED_SUCCESSFULLY";

	public ChatRoomCallback(String response) {
		super(response);
	}
}
