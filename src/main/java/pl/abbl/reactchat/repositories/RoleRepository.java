package pl.abbl.reactchat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.abbl.reactchat.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
