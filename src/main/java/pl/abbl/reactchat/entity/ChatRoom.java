package pl.abbl.reactchat.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import pl.abbl.reactchat.repository.enums.ChatRoomType;

import javax.persistence.*;

@Data
@Entity
public class ChatRoom {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@JsonIgnore
	private int ownerId;
	private String name;
	private String description;
	@Enumerated(EnumType.STRING)
	private ChatRoomType type;

	public ChatRoom(){}

	public ChatRoom(String name, String description, String type, int ownerId){
		this.name = name;
		this.description = description;
		this.type = ChatRoomType.valueOf(type);
		this.ownerId = ownerId;
	}
}
