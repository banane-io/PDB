package banane.io.pdb.model

import javax.persistence.*

@Entity
data class MapPoint(
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    @get:Id
    var id: Long,
    var x: Int,
    var y: Int,
    var zone: String?,
    @Enumerated(EnumType.STRING)
    var terrain: Terrain?
)
