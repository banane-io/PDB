package banane.io.pdb.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banane.io.pdb.model.MapPoint;
import banane.io.pdb.repository.MapPointRepository;

@RestController
@RequestMapping("/api/mapPoint")
public class MapPointController {
    @Autowired
    private MapPointRepository mapPointRepository;

    @GetMapping("/{id}")
    public MapPoint getZone(@PathVariable("id") Long zoneId){
        return mapPointRepository.findById(zoneId).get();
    }

}