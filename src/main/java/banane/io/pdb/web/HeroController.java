package banane.io.pdb.web;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import banane.io.pdb.model.Hero;
import banane.io.pdb.model.User;
import banane.io.pdb.repository.MapPointRepository;
import banane.io.pdb.repository.HeroRepository;
import banane.io.pdb.security.SecurityService;
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


    private static String VIEW_FOLDER = "player/";

    @GetMapping
    public Hero currentUserPlayer() {
        final User loggedInUser = securityService.findLoggedInUser();
        return loggedInUser.getHero();
    }

    @PostMapping
    public Hero create(@RequestBody Hero hero, BindingResult bindingResult) {
        User user = securityService.findLoggedInUser();
        checkArgument(user.getHero() == null, "The user already have have a hero");
        checkNotNull(hero);

        heroValidator.validate(hero, bindingResult);

        if (bindingResult.hasErrors()) {
            //TODO : Should return error here
        }

        hero.setOwner(user);
        hero.setCurrentZone(mapPointRepository.getOne(1L));
        heroRepository.save(hero);

        return hero;
    }

    private Map<String, ObjectError> getFieldErrors(BindingResult result) {
        Map<String, ObjectError> map = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            map.put(error.getField(), error);
        }
        return map;
    }
}
