package banane.io.pdb.model

import javax.persistence.*

@Entity
@Table(name = "role")
data class Role (
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    @get:Id
    var id: Long? = null,
    var name: String? = null,
    @get:ManyToMany(mappedBy = "roles")
    var users: Set<User>? = null

)