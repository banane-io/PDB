package banane.io.pdb.repository

import banane.io.pdb.model.Hero
import org.springframework.data.jpa.repository.JpaRepository

interface HeroRepository : JpaRepository<Hero?, Long?>