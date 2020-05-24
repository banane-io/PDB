package banane.io.pdb.repository

import banane.io.pdb.model.MapPoint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface MapPointRepository : JpaRepository<MapPoint?, Long?> {
    @Query("select m from MapPoint m where m.x >= :x1 and m.x <= :x2 and m.y >= :y1 and m.y <= :y2 order by m.y, m.x ")
    fun loadGrid(@Param("x1") x1: Int?, @Param("x2") x2: Int?, @Param("y1") y1: Int?, @Param("y2") y2: Int?): List<MapPoint?>?

    fun findMapPointByXAndY(x: Int?, y: Int?): Optional<MapPoint?>?
}