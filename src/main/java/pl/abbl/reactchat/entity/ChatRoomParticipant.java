package pl.abbl.reactchat.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class ChatRoomParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int roomId;
    private int userId;

    public ChatRoomParticipant(){

    }

    public ChatRoomParticipant(int roomId, int userId){
        this.roomId = roomId;
        this.userId = userId;
    }
}
