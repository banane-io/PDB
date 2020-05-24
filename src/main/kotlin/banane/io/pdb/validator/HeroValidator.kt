package banane.io.pdb.validator

import banane.io.pdb.model.Hero
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator

@Component
class HeroValidator : Validator {
    override fun supports(clazz: Class<*>): Boolean {
        return Hero::class.java == clazz
    }

    override fun validate(target: Any, errors: Errors) {
        val hero = target as Hero
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty")
        if (hero.username?.length!! < 6 || hero.username?.length!! > 32) {
            errors.rejectValue("username", "Size.userForm.username")
        }
        if (hero.agility!! + hero.intelligence!! + hero.strength!! != 15) {
            errors.rejectValue("agility", "Size.userForm.username")
        }
    }
}