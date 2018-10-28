package pl.abbl.reactchat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Data
@Entity
public class ChatMessage {
	@JsonIgnore
	private static final SimpleDateFormat timeStampPattern = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int roomIndependentMessageId;
	private int roomId;
	private String sender;
	private String message;
	private Timestamp timeStamp;


	public ChatMessage() {

	}

	public ChatMessage(int roomIndependentMessageId, int roomId, String sender, String message){
		this.roomIndependentMessageId = roomIndependentMessageId;
		this.roomId = roomId;
		this.sender = sender;
		this.message = message;
		setTimeStamp();
	}

	/**
	 * Sets timeStamp to current time.
	 */
	public void setTimeStamp(){
		timeStamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
	}
}
