package banane.io.pdb.validator;

import banane.io.pdb.model.Hero;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class HeroValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Hero.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Hero hero = (Hero) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (hero.getUsername().length() < 6 || hero.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
    }
}
