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

import banane.io.pdb.model.Player;
import banane.io.pdb.model.User;
import banane.io.pdb.repository.MapPointRepository;
import banane.io.pdb.repository.PlayerRepository;
import banane.io.pdb.security.SecurityService;
import banane.io.pdb.validator.PlayerValidator;

@RestController
@RequestMapping("/api/player")
public class PlayerController {
    @Autowired
    private PlayerValidator playerValidator;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private MapPointRepository mapPointRepository;

    @Autowired
    private SecurityService securityService;


    private static String VIEW_FOLDER = "player/";

    @GetMapping
    public Player currentUserPlayer() {
        final User loggedInUser = securityService.findLoggedInUser();
        return loggedInUser.getPlayer();
    }

    @PostMapping("/create")
    public Player create(@RequestBody Player player, BindingResult bindingResult) {
        User user = securityService.findLoggedInUser();
        checkArgument(user.getPlayer() == null, "The user already have have a player");
        checkNotNull(player);

        playerValidator.validate(player, bindingResult);

        if (bindingResult.hasErrors()) {
            //TODO : Should return error here
        }

        player.setOwner(user);
        player.setCurrentZone(mapPointRepository.getOne(1L));
        playerRepository.save(player);

        return player;
    }

    private Map<String, ObjectError> getFieldErrors(BindingResult result) {
        Map<String, ObjectError> map = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            map.put(error.getField(), error);
        }
        return map;
    }
}
