package pl.abbl.reactchat.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="user") //For sake of spring security authentication.
public class ChatUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private int active;

	public ChatUser(){}

	public ChatUser(String username, String password, int active){
		this.username = username;
		this.password = password;
		this.active = active;
	}
}
