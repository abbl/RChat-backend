package pl.abbl.reactchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.abbl.reactchat.entity.ChatRoomParticipant;

import java.util.List;

public interface ChatRoomParticipantsRepository extends JpaRepository<ChatRoomParticipant, Integer> {
    @Query("SELECT p FROM ChatRoomParticipant p WHERE p.roomId = :roomId AND p.userId = :userId")
    ChatRoomParticipant isUserParticipantOfChatRoom(@Param("userId") int userId, @Param("roomId") int roomId);
    @Query("SELECT p FROM ChatRoomParticipant p WHERE p.userId = :userId")
    List<ChatRoomParticipant> findChatRoomsByUserId(@Param("userId") int userId);
}
