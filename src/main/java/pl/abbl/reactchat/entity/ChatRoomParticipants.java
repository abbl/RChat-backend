package pl.abbl.reactchat.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class ChatRoomParticipants {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int roomId;
    private int userId;
}
