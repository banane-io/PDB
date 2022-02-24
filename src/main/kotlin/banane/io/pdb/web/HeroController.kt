package banane.io.pdb.web

import banane.io.pdb.model.Hero
import banane.io.pdb.model.MapPoint
import banane.io.pdb.repository.HeroRepository
import banane.io.pdb.repository.MapPointRepository
import banane.io.pdb.security.SecurityService
import banane.io.pdb.service.MapPointService
import banane.io.pdb.validator.HeroValidator
import com.google.common.base.Preconditions
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.ObjectError
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/hero")
class HeroController {
    @Autowired
    private val heroValidator: HeroValidator? = null
    @Autowired
    private val heroRepository: HeroRepository? = null
    @Autowired
    private val mapPointRepository: MapPointRepository? = null
    @Autowired
    private val securityService: SecurityService? = null
    @Autowired
    private val mapPointService: MapPointService? = null

    @GetMapping
    fun currentUserPlayer(): Hero? {
        val loggedInUser = securityService!!.findLoggedInUser()
        return loggedInUser?.hero
    }

    @PostMapping
    fun create(@RequestBody hero: Hero, bindingResult: BindingResult): ResponseEntity<*> {
        val user = securityService!!.findLoggedInUser()
        Preconditions.checkArgument(user?.hero == null, "The user already have have a hero")
        Preconditions.checkNotNull(hero)
        logger.debug("Validating hero")
        heroValidator!!.validate(hero, bindingResult)
        if (bindingResult.hasErrors()) {
            logger.debug("Hero is not valid, returning BAD_REQUEST")
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getFieldErrors(bindingResult))
        }
        hero.owner = user
        hero.currentZone = mapPointRepository!!.getById(1L)
        hero.stone = 0
        hero.wood = 0
        heroRepository!!.save(hero)
        logger.info("Creation of hero : {} successful", hero.username)
        return ResponseEntity.status(HttpStatus.OK).body(hero)
    }

    @PostMapping("/movePlayer/{id}")
    fun movePlayer(@PathVariable("id") mapPointId: Long): ResponseEntity<*> {
        logger.debug("Starting to move hero to the zone with id : {}", mapPointId.toString())
        val currentHero = securityService!!.findLoggedInUser()?.hero ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hero should not be null")
        val mapPointToMove = mapPointRepository!!.findById(mapPointId)
        val newMapPoint = mapPointToMove.get()
        logger.info("Moving hero {} to the zone with id : {}", currentHero.username, mapPointId.toString())
        mapPointService!!.movePlayer(currentHero, newMapPoint)
        return ResponseEntity.status(HttpStatus.OK).body(newMapPoint)
    }

    private fun getFieldErrors(result: BindingResult): Map<String, ObjectError> {
        val map: MutableMap<String, ObjectError> = HashMap()
        for (error in result.fieldErrors) {
            map[error.field] = error
        }
        return map
    }

    companion object {
        private val logger = LoggerFactory.getLogger(HeroController::class.java)
    }
}