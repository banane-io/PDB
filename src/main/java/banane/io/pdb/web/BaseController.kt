package banane.io.pdb.web

import banane.io.pdb.security.SecurityService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/base")
class BaseController {
    @Autowired
    private val securityService: SecurityService? = null

    /**
     * Load a grid of point with the center being the mapPoint passed in parameter
     *
     */
    @GetMapping("/resources")
    fun resources(): ResponseEntity<List<Int?>> {
        logger.info("Fetching base ressources")
        val base = securityService!!.findLoggedInUser()?.hero?.base
        val resourcesInBase: MutableList<Int?> = ArrayList(4)
        resourcesInBase.add(base?.stone)
        resourcesInBase.add(base?.wood)
        return ResponseEntity.status(HttpStatus.OK).body(resourcesInBase)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(BaseController::class.java)
    }
}