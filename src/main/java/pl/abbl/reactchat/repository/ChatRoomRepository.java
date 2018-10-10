package pl.abbl.reactchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.abbl.reactchat.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {

}
