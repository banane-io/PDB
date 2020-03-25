package banane.io.pdb.repository

import banane.io.pdb.model.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role?, Long?>