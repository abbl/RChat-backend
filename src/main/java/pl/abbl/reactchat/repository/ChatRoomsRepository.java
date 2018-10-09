package pl.abbl.reactchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.abbl.reactchat.model.ChatRoom;

public interface ChatRoomsRepository extends JpaRepository<ChatRoom, Integer> {

}
