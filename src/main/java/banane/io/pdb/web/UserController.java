package banane.io.pdb.web;

import banane.io.pdb.model.User;
import banane.io.pdb.security.SecurityService;
import banane.io.pdb.service.UserService;
import banane.io.pdb.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    //@Autowired
    //private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @PostMapping(value = "/registration")
    public User registration(@RequestBody User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return userForm;
        }

        final User user = userService.save(userForm);

        //securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return user;
    }

}
