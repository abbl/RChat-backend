package pl.abbl.reactchat.entities;

import lombok.Data;
import pl.abbl.reactchat.entities.enums.RoomRightLevel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * This entity represents {@link ChatUser} right in certain ChatRoom.
 *
 * @Version 1.0
 * @Since 2018-11-9
 */
@Data
@Entity
public class RoomRight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userId;
    private int roomId;
    private RoomRightLevel rightLevel;

    public RoomRight(){
    }

    public RoomRight(int id, int userId, int roomId, RoomRightLevel rightLevel){
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.rightLevel = rightLevel;
    }
}
