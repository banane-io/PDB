package banane.io.pdb.model

import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
data class Hero(
        @get:GeneratedValue(strategy = GenerationType.IDENTITY)
        @get:Id
        var id: Long? = null,
        var username: String? = null,
        @get:JoinColumn(name = "user_id", unique = true)
        @get:OneToOne(fetch = FetchType.LAZY)
        @JsonIgnore
        var owner: User? = null,
        @get:JoinColumn(name = "map_point_id", nullable = false)
        @get:ManyToOne(fetch = FetchType.EAGER, optional = false)
        var currentZone: MapPoint? = null,
        var strength: Int? = null,
        var agility: Int? = null,
        var intelligence: Int? = null,
        var hp: Int? = null,
        var mana: Int? = null,
        var wood: Int? = null,
        var stone: Int? = null,
        @get:OneToOne(mappedBy = "owner", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
        @JsonIgnore
        var base: Base? = null
) {
    @get:JsonGetter("currentZone")
    @get:Transient
    val currentZoneJson: Long?
        get() = currentZone?.id

}