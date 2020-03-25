package banane.io.pdb.service

import banane.io.pdb.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
open class UserDetailsServiceImpl : UserDetailsService {
    @Autowired
    private val userRepository: UserRepository? = null

    @Transactional(readOnly = true)
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val optionalUser = userRepository!!.findByUsername(username)
        val grantedAuthorities: MutableSet<GrantedAuthority> = HashSet()
        return if (optionalUser!!.isPresent) {
            val user = optionalUser.get()
            for (role in user.roles!!) {
                grantedAuthorities.add(SimpleGrantedAuthority(role?.name))
            }
            User(user.username, user.password, grantedAuthorities)
        } else {
            throw UsernameNotFoundException("User |$username| not found")
        }
    }
}