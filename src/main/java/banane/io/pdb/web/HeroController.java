package banane.io.pdb.web;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import banane.io.pdb.model.Hero;
import banane.io.pdb.model.MapPoint;
import banane.io.pdb.model.User;
import banane.io.pdb.repository.MapPointRepository;
import banane.io.pdb.repository.HeroRepository;
import banane.io.pdb.security.SecurityService;
import banane.io.pdb.service.MapPointService;
import banane.io.pdb.validator.HeroValidator;

@RestController
@RequestMapping("/api/hero")
public class HeroController {
    @Autowired
    private HeroValidator heroValidator;

    @Autowired
    private HeroRepository heroRepository;

    @Autowired
    private MapPointRepository mapPointRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private MapPointService mapPointService;

    private static final Logger logger = LoggerFactory.getLogger(HeroController.class);

    @GetMapping
    public Hero currentUserPlayer() {
        final User loggedInUser = securityService.findLoggedInUser();
        return loggedInUser.getHero();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Hero hero, BindingResult bindingResult) {
        User user = securityService.findLoggedInUser();
        checkArgument(user.getHero() == null, "The user already have have a hero");
        checkNotNull(hero);
        
        logger.debug("Validating hero");
        heroValidator.validate(hero, bindingResult);

        if (bindingResult.hasErrors()) {
            logger.debug("Hero is not valid, returning BAD_REQUEST");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getFieldErrors(bindingResult));
        }

        hero.setOwner(user);
        hero.setCurrentZone(mapPointRepository.getOne(1L));
        heroRepository.save(hero);
        logger.info("Creation of hero : " + hero.getUsername() + " successful");
        return ResponseEntity.status(HttpStatus.OK).body(hero);
    }

    @PostMapping("/movePlayer/{id}")
    public MapPoint movePlayer(@PathVariable("id") Long mapPointId) {
        logger.debug("Starting to move hero to the zone with id : " + mapPointId.toString());
        final Hero currentHero = securityService.findLoggedInUser().getHero();
        final Optional<MapPoint> mapPointToMove = mapPointRepository.findById(mapPointId);
        MapPoint newMapPoint = mapPointToMove.get();
        logger.info("Moving hero " + currentHero.getUsername() + " to the zone with id : " + mapPointId.toString());
        mapPointService.movePlayer(currentHero, newMapPoint);
        return newMapPoint;
    }

    private Map<String, ObjectError> getFieldErrors(BindingResult result) {
        Map<String, ObjectError> map = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            map.put(error.getField(), error);
        }
        return map;
    }
}
