package banane.io.pdb.web;

import banane.io.pdb.model.MapPoint;
import banane.io.pdb.model.Player;
import banane.io.pdb.repository.MapPointRepository;
import banane.io.pdb.security.SecurityService;
import banane.io.pdb.service.MapPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/grid")
public class GridController {

    @Autowired
    private MapPointRepository mapPointRepository;

    @Autowired
    private MapPointService mapPointService;

    //@Autowired
    //private SecurityService securityService;

    @GetMapping
    public List<List<MapPoint>> grid(Model model) {
        final MapPoint point = mapPointRepository.findById(1L).get();//TODO Implement the correct way for this securityService.findLoggedInUser().getPlayer().getCurrentZone();
        final List<MapPoint> mapPoints = mapPointService.loadGrid(point);

        final Map<Integer, List<MapPoint>> mapPointsGrouped = mapPoints.stream().collect(Collectors.groupingBy(p -> p.getY()));
        final List<List<MapPoint>> mapPointsForGrid = new ArrayList<>(mapPointsGrouped.values());

        model.addAttribute("mapPointsForGrid", mapPointsForGrid);
        model.addAttribute("mapPoint", point);
        model.addAttribute("neighbors", mapPointService.neighbors(point));

        return mapPointsForGrid;
    }

    @GetMapping("/movePlayer")
    public String movePlayer(@RequestParam("mapPoint") Long mapPoint, Model model) {
        final Player currentPlayer = new Player();//TODO Implement the correct way for this securityService.findLoggedInUser().getPlayer();
        final Optional<MapPoint> mapPointToMove = mapPointRepository.findById(mapPoint);
        mapPointService.movePlayer(currentPlayer, mapPointToMove.get());
        return "redirect:/grid";
    }
}
