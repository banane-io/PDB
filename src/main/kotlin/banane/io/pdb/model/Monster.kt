package banane.io.pdb.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Monster(
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    @get:Id
    var id: Long? = null,
    var name: String,
    var hp: Int,
    var attack: Int,
    var defense: Int
    )
