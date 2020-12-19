package banane.io.pdb.repository

import banane.io.pdb.model.Battle
import org.springframework.data.jpa.repository.JpaRepository

interface BattleRepository : JpaRepository<Battle?, Long?>