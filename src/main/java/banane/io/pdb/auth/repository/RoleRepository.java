package banane.io.pdb.auth.repository;

import banane.io.pdb.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
