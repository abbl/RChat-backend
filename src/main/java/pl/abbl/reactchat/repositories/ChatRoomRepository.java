package pl.abbl.reactchat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.abbl.reactchat.entities.ChatRoom;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
    @Query("SELECT r FROM ChatRoom r WHERE r.name=:name")
    ChatRoom findByName(@Param("name") String name);
    @Query("SELECT r FROM ChatRoom r WHERE r.id IN :roomIdList")
    List<ChatRoom> findChatRoomsByListOfId(@Param("roomIdList") List<Integer> roomIdList);
}
