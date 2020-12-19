package banane.io.pdb.repository

import banane.io.pdb.model.Monster
import org.springframework.data.jpa.repository.JpaRepository

interface MonsterRepository: JpaRepository<Monster?, Long?>