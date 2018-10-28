package pl.abbl.reactchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.abbl.reactchat.entity.ChatUser;

public interface UserRepository extends JpaRepository<ChatUser, Long>, UserRepositoryCustom {
    @Query("SELECT u FROM ChatUser u WHERE u.username = :username")
    ChatUser findByUsername(@Param("username") String username);
    @Query("SELECT u FROM ChatUser u WHERE u.id = :id")
    ChatUser findByIdInt(@Param("id") int id);
}
