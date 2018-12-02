package pl.abbl.reactchat.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import pl.abbl.reactchat.definitions.enums.RoomRightLevel;

import javax.persistence.*;

/**
 * This entity represents {@link ChatUser} right in certain {@link ChatRoom}.
 * Available rights are listed in {@link RoomRightLevel}
 * @Version 1.1
 * @Since 2018-11-9
 */
@Data
@Entity
public class RoomRight {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonIgnore
    private int userId;
    @JsonIgnore
    private int roomId;
    @Enumerated(EnumType.STRING)
    private RoomRightLevel rightLevel;

    public RoomRight(){
    }

    public RoomRight(int userId, int roomId, RoomRightLevel rightLevel){
        this.userId = userId;
        this.roomId = roomId;
        this.rightLevel = rightLevel;
    }
}
