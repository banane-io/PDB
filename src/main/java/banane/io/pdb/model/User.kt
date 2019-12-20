package banane.io.pdb.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "users")
class User {
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    @get:Id
    var id: Long? = null
    var username: String? = null
    //@JsonIgnore
    var password: String? = null
    @get:JoinTable(name = "user_role", joinColumns = [JoinColumn(name = "user_id")], inverseJoinColumns = [JoinColumn(name = "role_id")])
    @get:ManyToMany
    var roles: Set<Role?>? = null
    @get:OneToOne(mappedBy = "owner", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonIgnore
    var hero: Hero? = null

}