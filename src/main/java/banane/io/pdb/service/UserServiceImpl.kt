package banane.io.pdb.service

import banane.io.pdb.model.User
import banane.io.pdb.repository.RoleRepository
import banane.io.pdb.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl : UserService {
    @Autowired
    private val userRepository: UserRepository? = null
    @Autowired
    private val roleRepository: RoleRepository? = null
    @Autowired
    private val bCryptPasswordEncoder: BCryptPasswordEncoder? = null

    override fun save(user: User): User {
        user.password = bCryptPasswordEncoder!!.encode(user.password)
        user.roles = HashSet(roleRepository!!.findAll())
        return userRepository!!.save(user)
    }

    override fun findByUsername(username: String?): Optional<User?>? {
        return userRepository!!.findByUsername(username)
    }
}