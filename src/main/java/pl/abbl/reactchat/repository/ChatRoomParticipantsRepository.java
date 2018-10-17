package pl.abbl.reactchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.abbl.reactchat.entity.ChatRoomParticipants;

import java.util.List;

public interface ChatRoomParticipantsRepository extends JpaRepository<ChatRoomParticipants, Integer> {
    @Query("SELECT p FROM ChatRoomParticipants p WHERE p.roomId = :roomId AND p.userId = :userId")
    ChatRoomParticipants isUserParticipantOfChatRoom(@Param("userId") int userId, @Param("roomId") int roomId);
    @Query("SELECT p FROM ChatRoomParticipants p WHERE p.userId = :userId")
    List<ChatRoomParticipants> findChatRoomsByUserId(@Param("userId") int userId);
}
