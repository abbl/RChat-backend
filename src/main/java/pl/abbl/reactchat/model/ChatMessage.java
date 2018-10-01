package pl.abbl.reactchat.model;

public class ChatMessage {
	private final long id;
	private final String message;
	
	public ChatMessage(long id, String message) {
		this.id = id;
		this.message = message;
	}
	
	public long getId() {
		return id;
	}
	
	public String getMessage() {
		return message;
	}
}
