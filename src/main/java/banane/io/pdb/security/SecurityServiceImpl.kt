package banane.io.pdb.security

import banane.io.pdb.model.User
import banane.io.pdb.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
open class SecurityServiceImpl : SecurityService {
    @Autowired
    private val authenticationManager: AuthenticationManager? = null
    @Autowired
    private val userDetailsService: UserDetailsService? = null
    @Autowired
    private val userService: UserService? = null

    override fun findLoggedInUsername(): String? {
        logger.debug("Finding logged in user")
        val userDetails = SecurityContextHolder.getContext().authentication.principal
        if (userDetails is UserDetails) {
            val username = userDetails.username
            logger.debug("Find logged in user with username : {}", username)
            return username
        }
        logger.debug("No user found")
        return null
    }

    override fun findLoggedInUser(): User? {
        logger.debug("Trying to find current logged in user")
        return userService!!.findByUsername(findLoggedInUsername())!!.orElseThrow { IllegalStateException("A logged in user should have a User of the same name") }
    }

    override fun autologin(username: String?, password: String?) {
        val userDetails = userDetailsService!!.loadUserByUsername(username)
        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(userDetails, password, userDetails.authorities)
        authenticationManager!!.authenticate(usernamePasswordAuthenticationToken)
        if (usernamePasswordAuthenticationToken.isAuthenticated) {
            SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            logger.debug("Auto login {} successfully!", username)
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(SecurityServiceImpl::class.java)
    }
}