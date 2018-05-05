package banane.io.pdb.web;

import banane.io.pdb.model.Player;
import banane.io.pdb.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/player")
public class PlayerController {

    private static String VIEW_FOLDER = "/player/";

    @GetMapping("/creation")
    public String creation(Model model) {

        return VIEW_FOLDER + "creation";
    }

    @PostMapping("/creation")
    public String creation(@ModelAttribute("player") Player player, BindingResult bindingResult, Model model) {

        return "redirect:/";
    }
}
