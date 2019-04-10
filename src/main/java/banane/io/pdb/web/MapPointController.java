package banane.io.pdb.web;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banane.io.pdb.model.MapPoint;
import banane.io.pdb.model.Terrain;
import banane.io.pdb.repository.MapPointRepository;
import banane.io.pdb.security.SecurityService;

@RestController
@RequestMapping("/api/mapPoint")
public class MapPointController {

    @Autowired
    private MapPointRepository mapPointRepository;

    @Autowired
    private SecurityService securityService;

    private static final Logger logger = LoggerFactory.getLogger(MapPointController.class);

    @GetMapping("/{id}")
    public MapPoint getZone(@PathVariable("id") Long zoneId) {
        logger.info("Fetching data for mapPoint: {}", zoneId.toString());
        MapPoint mapPoint = mapPointRepository.findById(zoneId).get();
        logger.info("Fetched mapPoint: {} with x: {} and y: {}", zoneId.toString(), mapPoint.getX(), mapPoint.getY());
        return mapPoint;
    }

    @GetMapping("/{id}/actions")
    public List<String> getActions(@PathVariable("id") Long zoneId) {
        logger.info("Fetching data for loading actions of mapPoint: {}", zoneId.toString());
        MapPoint mapPoint = mapPointRepository.findById(zoneId).get();
        logger.info("Fetched mapPoint: {} with x: {} and y: {}", zoneId.toString(), mapPoint.getX(), mapPoint.getY());
        Terrain terrain = mapPoint.getTerrain();
        List<String> actions = new LinkedList<>();
        if (Terrain.MOUNTAIN.equals(terrain)) {
            actions.add("MINE");
        } else if (Terrain.FOREST.equals(terrain)) {
            actions.add("LOGGING");
            actions.add("MINE");
        }

        return actions;
    }

    @PostMapping("/{id}/action")
    public ResponseEntity<?> doAction(@PathVariable("id") Long zoneId, @RequestBody String action) {
        logger.info("Processing the action for the zone {}", zoneId);
        if (Strings.isNullOrEmpty(action)) {
            logger.warn("Action was null or empty, {} sent a BAD_REQUEST", securityService.findLoggedInUsername());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error on the action");
        }
        JsonParser jsonParser = new BasicJsonParser();
        Map<String, Object> parseMap = jsonParser.parseMap(action);
        String object = (String) parseMap.get("action");
        if (object.equalsIgnoreCase("MINE")) {
            logger.info("Procressing MINE ACTION for zone: {} player : {}", zoneId, securityService.findLoggedInUsername());
        } else if (object.equalsIgnoreCase("LOGGING")) {
            logger.info("Procressing LOGGING ACTION for zone: {} player : {}", zoneId, securityService.findLoggedInUsername());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("");
    }
}