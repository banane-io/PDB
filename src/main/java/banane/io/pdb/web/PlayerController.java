package banane.io.pdb.web;

import banane.io.pdb.model.Player;
import banane.io.pdb.model.User;
import banane.io.pdb.repository.PlayerRepository;
import banane.io.pdb.security.SecurityService;
import banane.io.pdb.validator.PlayerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/player")
public class PlayerController {
    @Autowired
    private PlayerValidator playerValidator;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private SecurityService securityService;


    private static String VIEW_FOLDER = "player/";

    @GetMapping
    public String show() {

        return VIEW_FOLDER + "show";
    }

    @GetMapping("/creation")
    public String creation(Model model) {
        model.addAttribute("player", new Player());
        return VIEW_FOLDER + "creation";
    }

    @PostMapping("/creation")
    public String creation(@ModelAttribute("player") Player player, BindingResult bindingResult, Model model) {
        playerValidator.validate(player, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("formErrors", bindingResult.getAllErrors());
            model.addAttribute("fieldErrors", getFieldErrors(bindingResult));
            return VIEW_FOLDER + "creation";
        }
        User user = securityService.findLoggedInUser();
        player.setOwner(user);
        playerRepository.save(player);

        return "redirect:/";
    }

    private Map<String, ObjectError> getFieldErrors(BindingResult result) {
        Map<String, ObjectError> map = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            map.put(error.getField(), error);
        }
        return map;
    }
}
