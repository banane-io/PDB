package banane.io.pdb.model

import javax.persistence.*

@Entity
class MapPoint {
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    @get:Id
    var id: Long? = null
    var x = 0
    var y = 0
    var zone: String? = null
    @Enumerated(EnumType.STRING)
    var terrain: Terrain? = null

}