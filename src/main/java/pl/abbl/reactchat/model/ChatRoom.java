package pl.abbl.reactchat.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ChatRoom {
	private long id;
	private String name;
	@JsonIgnore
	private List<ChatMessage> messages;
	
	public ChatRoom(long id, String name) {
		this.id = id;
		this.name = name;
		messages = new ArrayList<>();
	}
	
	public void addMessage(String message) {
		messages.add(new ChatMessage(messages.size() + 1, message));
	}
	
	public String getName() {
		return name;
	}
	
	public long getId() {
		return id;
	}
	
	public List<ChatMessage> getMessages(){
		return messages;
	}
}
