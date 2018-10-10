package pl.abbl.reactchat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	private int roomId;
	private String sender;
	private String message;
	private Timestamp timeStamp;

	public ChatMessage() {}
}
