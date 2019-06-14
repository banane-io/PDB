package banane.io.pdb.web;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import banane.io.pdb.model.User;
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

import banane.io.pdb.model.Action;
import banane.io.pdb.model.MapPoint;
import banane.io.pdb.model.Terrain;
import banane.io.pdb.repository.MapPointRepository;
import banane.io.pdb.security.SecurityService;
import banane.io.pdb.service.ActionService;

@RestController
@RequestMapping("/api/mapPoint")
public class MapPointController {

    @Autowired
    private MapPointRepository mapPointRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private ActionService actionService;

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
        List<String> actions = actionService.getAvailablesActionsFromMapPoint(mapPoint);

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
        Optional<Action> parsedValue = Action.parse(object);

        if(!parsedValue.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid action value");
        }

        Action actionToExecute = parsedValue.get();
        logger.info("Processing {} for zone: {} player : {}", actionToExecute, zoneId, securityService.findLoggedInUsername());
        actionService.executeAction(actionToExecute);
        
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("");
    }
}