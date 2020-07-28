package banane.io.pdb.web

import banane.io.pdb.model.Action
import banane.io.pdb.model.MapPoint
import banane.io.pdb.repository.MapPointRepository
import banane.io.pdb.security.SecurityService
import banane.io.pdb.service.ActionService
import com.google.common.base.Strings
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.json.BasicJsonParser
import org.springframework.boot.json.JsonParser
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.stream.Collectors

@RestController
@RequestMapping("/api/mapPoint")
class MapPointController {
    @Autowired
    private val mapPointRepository: MapPointRepository? = null
    @Autowired
    private val securityService: SecurityService? = null
    @Autowired
    private val actionService: ActionService? = null

    @GetMapping("/{id}")
    fun getZone(@PathVariable("id") zoneId: Long): MapPoint {
        logger.info("Fetching data for mapPoint: {}", zoneId.toString())
        val mapPoint = mapPointRepository!!.findById(zoneId).get()
        logger.info("Fetched mapPoint: {} with x: {} and y: {}", zoneId.toString(), mapPoint.x, mapPoint.y)
        return mapPoint
    }

    @GetMapping("/{id}/actions")
    fun getActions(@PathVariable("id") zoneId: Long): List<String?> {
        logger.info("Fetching data for loading actions of mapPoint: {}", zoneId.toString())
        val mapPoint = mapPointRepository!!.findById(zoneId).get()
        logger.info("Fetched mapPoint: {} with x: {} and y: {}", zoneId.toString(), mapPoint.x, mapPoint.y)
        val actions = actionService!!.getAvailablesActionsFromMapPoint(mapPoint)
        return actions.stream().map { obj: Action -> obj.nameOfAction }.collect(Collectors.toList())
    }

    @PostMapping("/{id}/action")
    fun doAction(@PathVariable("id") zoneId: Long?, @RequestBody action: String?): ResponseEntity<*> {
        logger.info("Processing the action for the zone {}", zoneId)
        if (Strings.isNullOrEmpty(action)) {
            logger.warn("Action was null or empty, {} sent a BAD_REQUEST", securityService!!.findLoggedInUsername())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error on the action")
        }
        val jsonParser: JsonParser = BasicJsonParser()
        val parseMap = jsonParser.parseMap(action)
        val `object` = parseMap["action"] as String?
        val parsedValue: Optional<Action> = Action.Companion.parse(`object`)
        if (!parsedValue.isPresent) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid action value")
        }
        val actionToExecute = parsedValue.get()
        logger.info("Processing {} for zone: {} player : {}", actionToExecute, zoneId, securityService!!.findLoggedInUsername())
        actionService!!.executeAction(actionToExecute)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("")
    }

    companion object {
        private val logger = LoggerFactory.getLogger(MapPointController::class.java)
    }
}