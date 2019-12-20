package banane.io.pdb.security

import banane.io.pdb.model.User

interface SecurityService {
    fun findLoggedInUsername(): String?
    fun findLoggedInUser(): User?
    fun autologin(username: String?, password: String?)
}