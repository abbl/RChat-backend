package pl.abbl.reactchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.abbl.reactchat.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
