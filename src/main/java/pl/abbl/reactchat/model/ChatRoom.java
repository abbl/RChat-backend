package pl.abbl.reactchat.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ChatRoom {
	@JsonIgnore
	private static int idCount = 0;
	@JsonIgnore
	private List<ChatMessage> messages;

	private long id;
	private String name;
	private String description;
	private String ownerToken;
	
	public ChatRoom(String ownerToken, String name, String description) {
		this.id = idCount++;
		this.ownerToken = ownerToken;
		this.name = name;
		this.description = description;
		messages = new ArrayList<>();
	}
	
	public void addMessage(String sender, String message) {
		messages.add(new ChatMessage(messages.size() + 1, sender, message));
	}
	
	public String getName() {
		return name;
	}
	
	public long getId() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public List<ChatMessage> getMessages(){
		return messages;
	}
}
