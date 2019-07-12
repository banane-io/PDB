package banane.io.pdb.web;

import banane.io.pdb.model.Base;
import banane.io.pdb.model.Direction;
import banane.io.pdb.model.MapPoint;
import banane.io.pdb.repository.MapPointRepository;
import banane.io.pdb.security.SecurityService;
import banane.io.pdb.service.MapPointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@RestController
@RequestMapping("/api/base")
public class BaseController {

    @Autowired
    private SecurityService securityService;

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * Load a grid of point with the center being the mapPoint passed in parameter
     * 
     */
    @GetMapping("/resources")
    public ResponseEntity<List<Integer>> resources() {
        logger.info("Fetching base ressources");
        final Base base = securityService.findLoggedInUser().getHero().getBase();
        List<Integer> ressourcesInBase = new ArrayList<>(4);
        ressourcesInBase.add(base.getStone());
        ressourcesInBase.add(base.getWood());
        return ResponseEntity.status(HttpStatus.OK).body(ressourcesInBase);
    }

}
