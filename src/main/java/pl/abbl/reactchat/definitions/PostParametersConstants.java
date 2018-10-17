package pl.abbl.reactchat.definitions;

public abstract class PostParametersConstants {
    /*
     * ChatUser Entity.
     */
    public static final String USER_NAME = "username";
    public static final String USER_PASSWORD = "password";
    /*
     * ChatRoom Entity.
     */
    public static final String CHAT_ROOM_NAME = "chatRoomName";
    public static final String CHAT_ROOM_DESC = "chatRoomDesc";
    public static final String CHAT_ROOM_TYPE = "chatRoomType";
    public static final String[] AVAILABLE_CHAT_ROOM_TYPES = {"PUBLIC", "PRIVATE"};
    /*
     * ChatRoom Message
     */
    public static final String CHAT_ROOM_MESSAGE_CHAT_ROOM_ID = "chatRoomId";
    public static final String CHAT_ROOM_MESSAGE_MESSAGE = "chatRoomMessage";
    /*
     * ChatRoom Invite
     */
    public static final String CHAT_ROOM_INVITE_USERNAME = "inviteUsername";
    public static final String CHAT_ROOM_INVITE_ROOM_ID = "inviteRoomId";
}
