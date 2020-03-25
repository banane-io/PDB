package banane.io.pdb.repository

import banane.io.pdb.model.Base
import org.springframework.data.jpa.repository.JpaRepository

interface BaseRepository : JpaRepository<Base?, Long?>