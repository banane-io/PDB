package banane.io.pdb.web;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banane.io.pdb.model.Direction;
import banane.io.pdb.model.MapPoint;
import banane.io.pdb.repository.MapPointRepository;
import banane.io.pdb.service.MapPointService;

@RestController
@RequestMapping("/api/grid")
public class GridController {

    @Autowired
    private MapPointRepository mapPointRepository;

    @Autowired
    private MapPointService mapPointService;

    private static final Logger logger = LoggerFactory.getLogger(GridController.class);

    /**
     * Load a grid of point with the center being the mapPoint passed in parameter
     * 
     */
    @GetMapping("/{id}")
    public ResponseEntity<List<List<MapPoint>>> grid(@PathVariable("id") Long centralMapPointId) {
        checkNotNull(centralMapPointId);
        logger.info("Fetching grid aroung mapPoint : " + centralMapPointId.toString());
        final MapPoint centralPoint = mapPointRepository.findById(centralMapPointId)
                                                 .orElseThrow(() -> new IllegalArgumentException("MapPointPassed in parameter does not exist"));
        final List<MapPoint> mapPoints = mapPointService.loadGrid(centralPoint);

        final Map<Integer, List<MapPoint>> mapPointsGrouped = mapPoints.stream().collect(Collectors.groupingBy(p -> p.getY()));
        final List<List<MapPoint>> mapPointsForGrid = new ArrayList<>(mapPointsGrouped.values());

        return ResponseEntity.status(HttpStatus.OK).body(mapPointsForGrid);
    }

    /**
     * Load surrounding mapPoints to a central mapPoints. The data returned is Map of Directions associatied with each point.
     * 
     */
    @GetMapping("/neighbors/{id}")
    public ResponseEntity<Map<Direction, MapPoint>> neighbors(@PathVariable("id") Long centralMapPointId) {
        checkNotNull(centralMapPointId);
        logger.info("Fetching neighbors aroung mapPoint : " + centralMapPointId.toString());
        final MapPoint centralPoint = mapPointRepository.findById(centralMapPointId)
                                                 .orElseThrow(() -> new IllegalArgumentException("MapPointPassed in parameter does not exist"));
        return ResponseEntity.status(HttpStatus.OK).body(mapPointService.neighbors(centralPoint));
    }
}
