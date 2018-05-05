package banane.io.pdb.validator;

import banane.io.pdb.model.Player;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PlayerValidator  implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Player.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Player player = (Player) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (player.getUsername().length() < 6 || player.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
    }
}
