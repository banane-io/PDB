package banane.io.pdb.web;

import banane.io.pdb.model.MapPoint;
import banane.io.pdb.repository.MapPointRepository;
import banane.io.pdb.service.MapPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/grid")
public class GridController {

    @Autowired
    private MapPointRepository mapPointRepository;

    @Autowired
    private MapPointService mapPointService;

    @GetMapping
    public String index() {
        MapPoint point = mapPointRepository.getOne(1L);
        List<MapPoint> mapPoints = mapPointService.loadGrid(point);

        return "/grid/grid";
    }
}
