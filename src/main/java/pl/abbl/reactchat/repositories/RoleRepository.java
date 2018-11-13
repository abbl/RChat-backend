package pl.abbl.reactchat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.abbl.reactchat.models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
