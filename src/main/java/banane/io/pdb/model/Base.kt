package banane.io.pdb.model

import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class Base {
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    @get:Id
    var id: Long? = null
    @get:JoinColumn(name = "map_point_id", nullable = false)
    @get:ManyToOne(fetch = FetchType.EAGER, optional = false)
    var location: MapPoint? = null
    var wood: Int? = null
    var stone: Int? = null
    @get:JoinColumn(name = "hero_id", unique = true)
    @get:OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    var owner: Hero? = null

    @get:JsonGetter("location")
    @get:Transient
    val locationeJson: Long?
        get() = location?.id

}