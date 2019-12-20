package banane.io.pdb.web

import banane.io.pdb.model.Direction
import banane.io.pdb.model.MapPoint
import banane.io.pdb.repository.MapPointRepository
import banane.io.pdb.service.MapPointService
import com.google.common.base.Preconditions
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/grid")
class GridController {
    @Autowired
    private val mapPointRepository: MapPointRepository? = null
    @Autowired
    private val mapPointService: MapPointService? = null

    /**
     * Load a grid of point with the center being the mapPoint passed in parameter
     *
     */
    @GetMapping("/{id}")
    fun grid(@PathVariable("id") centralMapPointId: Long): ResponseEntity<List<List<MapPoint?>?>?> {
        Preconditions.checkNotNull(centralMapPointId)
        logger.info("Fetching grid aroung mapPoint : {}", centralMapPointId.toString())
        val centralPoint = mapPointRepository!!.findById(centralMapPointId)
                .orElseThrow { IllegalArgumentException("MapPointPassed in parameter does not exist") }!!
        val mapPointsForGrid = mapPointService!!.loadGrid(centralPoint)
        return ResponseEntity.status(HttpStatus.OK).body(mapPointsForGrid)
    }

    /**
     * Load surrounding mapPoints to a central mapPoints. The data returned is Map of Directions associatied with each point.
     *
     */
    @GetMapping("/neighbors/{id}")
    fun neighbors(@PathVariable("id") centralMapPointId: Long): ResponseEntity<Map<Direction, MapPoint>> {
        Preconditions.checkNotNull(centralMapPointId)
        logger.info("Fetching neighbors aroung mapPoint : {}", centralMapPointId.toString())
        val centralPoint = mapPointRepository!!.findById(centralMapPointId)
                .orElseThrow { IllegalArgumentException("MapPointPassed in parameter does not exist") }!!
        return ResponseEntity.status(HttpStatus.OK).body(mapPointService!!.neighbors(centralPoint))
    }

    companion object {
        private val logger = LoggerFactory.getLogger(GridController::class.java)
    }
}