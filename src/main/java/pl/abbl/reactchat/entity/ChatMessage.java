package pl.abbl.reactchat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Data
@Entity
public class ChatMessage {
	@JsonIgnore
	private static final SimpleDateFormat timeStampPattern = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String sender;
	private String message;
	private Timestamp timeStamp;
	private int roomId;

	public ChatMessage() {}
}