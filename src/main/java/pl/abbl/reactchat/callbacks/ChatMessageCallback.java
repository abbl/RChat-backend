package pl.abbl.reactchat.callbacks;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Deprecated
public class ChatMessageCallback extends AbstractCallback{
    @JsonIgnore
    public static final String INVALID_CHAT_ROOM_ID = "INVALID_CHAT_ROOM_ID";
    @JsonIgnore
    public static final String NOT_PARTICIPANT_OF_CHAT_ROOM = "NOT_PARTICIPANT_OF_CHAT_ROOM";
    @JsonIgnore
    public static final String MISSING_FIELD = "MISSING_FIELD";

    public ChatMessageCallback(String response) {
        super(response);
    }
}
