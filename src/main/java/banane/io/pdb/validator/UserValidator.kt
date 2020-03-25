package banane.io.pdb.validator

import banane.io.pdb.model.User
import banane.io.pdb.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator

@Component
class UserValidator : Validator {
    @Autowired
    private val userService: UserService? = null

    override fun supports(clazz: Class<*>): Boolean {
        return User::class.java == clazz
    }

    override fun validate(target: Any, errors: Errors) {
        val user = target as User
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty")
        if (user.username?.length!! < 6 || user.username?.length!! > 32) {
            errors.rejectValue("username", "Size.userForm.username")
        }
        if (userService!!.findByUsername(user.username)!!.isPresent) {
            errors.rejectValue("username", "Duplicate.userForm.username")
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty")
        if (user.password?.length!! < 8 || user.password?.length!! > 32) {
            errors.rejectValue("password", "Size.userForm.password")
        }
    }
}