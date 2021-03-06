package pl.abbl.reactchat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.abbl.reactchat.models.ChatUser;

public interface UserRepository extends JpaRepository<ChatUser, Long>{
    @Query("SELECT u FROM ChatUser u WHERE u.username = :username")
    ChatUser findByUsername(@Param("username") String username);

    @Query("SELECT u FROM ChatUser u WHERE u.id = :id")
    ChatUser findByIdInt(@Param("id") int id);
}
