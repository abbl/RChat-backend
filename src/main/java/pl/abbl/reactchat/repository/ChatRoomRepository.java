package pl.abbl.reactchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.abbl.reactchat.entity.ChatRoom;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
    @Query("SELECT r FROM ChatRoom r WHERE r.id = :roomId AND r.type = 'PRIVATE'")
    ChatRoom isChatRoomPrivate(@Param("roomId") int roomId);
    @Query("SELECT r FROM ChatRoom r WHERE r.id IN :roomIdList")
    List<ChatRoom> findChatRoomsByListOfId(@Param("roomIdList") List<Integer> roomIdList);
}
