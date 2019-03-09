package banane.io.pdb.web;

import banane.io.pdb.model.Direction;
import banane.io.pdb.model.MapPoint;
import banane.io.pdb.model.Player;
import banane.io.pdb.repository.MapPointRepository;
import banane.io.pdb.security.SecurityService;
import banane.io.pdb.service.MapPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@RestController
@RequestMapping("/api/grid")
public class GridController {

    @Autowired
    private MapPointRepository mapPointRepository;

    @Autowired
    private MapPointService mapPointService;

    //@Autowired
    //private SecurityService securityService;

    /**
     * Load a grid of point with the center being the mapPoint passed in parameter
     * 
     */
    @GetMapping("/{id}")
    public List<List<MapPoint>> grid(@PathVariable("id") Long centralMapPointId) {
        checkNotNull(centralMapPointId);
        final MapPoint centralPoint = mapPointRepository.findById(centralMapPointId)
                                                 .orElseThrow(() -> new IllegalArgumentException("MapPointPassed in parameter does not exist")));
        final List<MapPoint> mapPoints = mapPointService.loadGrid(centralPoint);

        final Map<Integer, List<MapPoint>> mapPointsGrouped = mapPoints.stream().collect(Collectors.groupingBy(p -> p.getY()));
        final List<List<MapPoint>> mapPointsForGrid = new ArrayList<>(mapPointsGrouped.values());

        return mapPointsForGrid;
    }

    /**
     * Load surrounding mapPoints to a central mapPoints. The data returned is Map of Directions associatied with each point.
     * 
     */
    @GetMapping("/neighbors/{id}")
    public Map<Direction, MapPoint> neighbors(@PathVariable("id") Long centralMapPointId) {
        checkNotNull(centralMapPointId);
        final MapPoint centralPoint = mapPointRepository.findById(centralMapPointId)
                                                 .orElseThrow(() -> new IllegalArgumentException("MapPointPassed in parameter does not exist"));
        return mapPointService.neighbors(centralPoint);
    }


    //TODO move this to the player controller. This does not belong here since it's strongly tied to the player, not the grid
    @GetMapping("/movePlayer")
    public String movePlayer(@RequestParam("mapPoint") Long mapPoint) {
        final Player currentPlayer = new Player();//TODO Implement the correct way for this securityService.findLoggedInUser().getPlayer();
        final Optional<MapPoint> mapPointToMove = mapPointRepository.findById(mapPoint);
        mapPointService.movePlayer(currentPlayer, mapPointToMove.get());
        return "redirect:/grid";
    }
}
