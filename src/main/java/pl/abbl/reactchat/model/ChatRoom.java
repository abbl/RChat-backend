package pl.abbl.reactchat.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import pl.abbl.reactchat.repository.enums.ChatRoomType;

import javax.persistence.*;

@Data
@Entity
public class ChatRoom {
	@JsonIgnore
	@OneToMany
	private List<ChatMessage> messages;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String description;
	@Enumerated(EnumType.STRING)
	private ChatRoomType type;

	public ChatRoom(){}
}
