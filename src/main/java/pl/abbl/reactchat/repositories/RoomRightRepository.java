package pl.abbl.reactchat.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.abbl.reactchat.entities.RoomRight;

import java.util.List;

public interface RoomRightRepository extends JpaRepository<RoomRight, Integer> {
    @Query("SELECT rr FROM RoomRight rr WHERE rr.userId = :userId AND rr.roomId = :roomId")
    RoomRight getRight(@Param("userId") int userId, @Param("roomId") int roomId);
    @Query("SELECT rr FROM RoomRight rr WHERE rr.userId = :userId")
    List<RoomRight> getRights(@Param("userId") int userId);
}
