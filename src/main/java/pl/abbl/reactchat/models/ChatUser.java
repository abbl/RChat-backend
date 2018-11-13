package pl.abbl.reactchat.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ChatUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String description;
	private int active;

	public ChatUser(){}

	public ChatUser(String username, String password, int active){
		this.username = username;
		this.password = password;
		this.active = active;
	}
}
