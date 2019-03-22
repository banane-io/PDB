package banane.io.pdb.web;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

        heroValidator.validate(hero, bindingResult);

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getFieldErrors(bindingResult));
        }

        hero.setOwner(user);
        hero.setCurrentZone(mapPointRepository.getOne(1L));
        heroRepository.save(hero);

        return ResponseEntity.status(HttpStatus.OK).body(hero);
    }

    @PostMapping("/movePlayer/{id}")
    public String movePlayer(@PathVariable("id") Long mapPoint) {
        final Hero currentHero = securityService.findLoggedInUser().getHero();
        final Optional<MapPoint> mapPointToMove = mapPointRepository.findById(mapPoint);
        mapPointService.movePlayer(currentHero, mapPointToMove.get());
        return "redirect:/grid";
    }

    private Map<String, ObjectError> getFieldErrors(BindingResult result) {
        Map<String, ObjectError> map = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            map.put(error.getField(), error);
        }
        return map;
    }
}
