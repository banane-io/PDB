package banane.io.pdb.web

import banane.io.pdb.model.User
import banane.io.pdb.service.UserService
import banane.io.pdb.validator.UserValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class UserController {
    @Autowired
    private val userService: UserService? = null
    @Autowired
    private val userValidator: UserValidator? = null

    @PostMapping(value = ["/registration"])
    fun registration(@RequestBody userForm: User, bindingResult: BindingResult, model: Model?): User? {
        userValidator!!.validate(userForm, bindingResult)
        return if (bindingResult.hasErrors()) {
            userForm
        } else userService!!.save(userForm)
    }
}