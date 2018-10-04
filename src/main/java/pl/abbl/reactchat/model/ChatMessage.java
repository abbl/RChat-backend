package pl.abbl.reactchat.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ChatMessage {
	private static final SimpleDateFormat timeStampPattern = new SimpleDateFormat("HH:mm:ss");
	
	private final long id;
	private String sender;
	private String timeStamp;
	private final String message;
	
	public ChatMessage(long id, String sender, String message ) {
		this.id = id;
		this.sender = sender;
		this.message = message;
		this.timeStamp = timeStampPattern.format(Calendar.getInstance().getTime());
	}
	
	public long getId() {
		return id;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getSender() {
		return sender;
	}
	
	public String getTimeStamp() {
		return timeStamp;
	}
}
