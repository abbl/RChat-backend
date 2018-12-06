package pl.abbl.reactchat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.abbl.reactchat.models.ChatRoom;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
    @Query("SELECT r FROM ChatRoom r WHERE r.name = :name")
    ChatRoom findByName(@Param("name") String name);

    @Query("SELECT r FROM ChatRoom r WHERE r.id IN :IdList")
    List<ChatRoom> findChatRoomsByListOfId(@Param("IdList") List<Integer> roomIdList);

    @Query("SELECT r FROM ChatRoom r WHERE r.id = :id")
    ChatRoom findById(@Param("id") int id);
}
