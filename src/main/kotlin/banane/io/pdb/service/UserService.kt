package banane.io.pdb.service

import banane.io.pdb.model.User
import java.util.*

interface UserService {
    fun save(user: User): User
    fun findByUsername(username: String?): Optional<User?>?
}