package pl.abbl.reactchat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.abbl.reactchat.entities.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
}
