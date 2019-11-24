package banane.io.pdb.web;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import banane.io.pdb.model.Base;
import banane.io.pdb.security.SecurityService;

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
