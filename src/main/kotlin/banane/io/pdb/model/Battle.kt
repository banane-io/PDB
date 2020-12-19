package banane.io.pdb.model

import javax.persistence.*

data class Battle(
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    @get:Id
    var id: Long? = null,
    @get:JoinColumn(name = "hero_id", nullable = false)
    @get:ManyToOne(fetch = FetchType.EAGER, optional = false)
    var hero: Hero,
    @get:JoinColumn(name = "monster_id", nullable = false)
    @get:ManyToOne(fetch = FetchType.EAGER, optional = false)
    var monster: Monster,
    @get:JoinColumn(name = "map_point_id", nullable = false)
    @get:ManyToOne(fetch = FetchType.EAGER, optional = false)
    var location: MapPoint
    )
