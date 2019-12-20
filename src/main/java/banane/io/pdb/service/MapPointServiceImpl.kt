package banane.io.pdb.service

import banane.io.pdb.model.Direction
import banane.io.pdb.model.Hero
import banane.io.pdb.model.MapPoint
import banane.io.pdb.repository.HeroRepository
import banane.io.pdb.repository.MapPointRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Stream

@Service
class MapPointServiceImpl : MapPointService {
    @Autowired
    private val mapPointRepository: MapPointRepository? = null
    @Autowired
    private val heroRepository: HeroRepository? = null
    private val X_MINIMUM_VALUE = 0
    private val Y_MINIMUM_VALUE = 0
    private val X_MAXIMUM_VALUE = 25
    private val Y_MAXIMUM_VALUE = 25
    private val GRID_RADIUS = 5
    override fun loadGrid(origin: MapPoint): List<List<MapPoint?>> {
        val xValues = calculateMinMax(GRID_RADIUS, X_MINIMUM_VALUE, X_MAXIMUM_VALUE, origin.x)
        val yValues = calculateMinMax(GRID_RADIUS, Y_MINIMUM_VALUE, Y_MAXIMUM_VALUE, origin.y)
        val mapPoints = mapPointRepository!!.loadGrid(xValues[0], xValues[1], yValues[0], yValues[1])
        val mapPointsForGrid: MutableList<List<MapPoint?>> = ArrayList()
        for (i in 0..10) {
            val row: MutableList<MapPoint?> = ArrayList()
            for (j in 0..10) {
                row.add(mapPoints!![i * 11 + j])
            }
            mapPointsForGrid.add(row)
        }
        return mapPointsForGrid
    }

    override fun neighbors(origin: MapPoint): Map<Direction, MapPoint> {
        val neighbors: MutableMap<Direction, MapPoint> = HashMap()
        for (direction in Direction.values()) {
            val result = mapPointRepository!!.findMapPointByXAndY(origin.x + direction.dx, origin.y + direction.dy)
            if (result!!.isPresent) {
                neighbors[direction] = result.get()
            }
        }
        return neighbors
    }

    override fun movePlayer(hero: Hero?, newPosition: MapPoint?) {
        if (hero != null) {
            hero.currentZone = newPosition
        }
        heroRepository!!.save(hero)
    }

    private fun calculateMinMax(rangeMap: Int, minMap: Int, maxMap: Int, position: Int): IntArray {
        val minMax = IntArray(2)
        var overflow = 0
        var underflow = 0
        val borderLeft = position - rangeMap
        val borderRight = position + rangeMap
        if (borderLeft < minMap) {
            overflow = Math.abs(borderLeft)
        }
        if (borderRight > maxMap) {
            underflow = borderRight - maxMap
        }
        var min = Stream.of(minMap, borderLeft).mapToInt { v: Int? -> v!! }.max().asInt
        min -= underflow
        var max = Stream.of(maxMap, borderRight).mapToInt { v: Int? -> v!! }.min().asInt
        max += overflow
        minMax[0] = min
        minMax[1] = max
        return minMax
    }
}