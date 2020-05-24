package banane.io.pdb.service

import banane.io.pdb.model.*
import banane.io.pdb.repository.BaseRepository
import banane.io.pdb.repository.HeroRepository
import banane.io.pdb.security.SecurityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
open class ActionServiceImpl(private val securityService: SecurityService, private val heroRepository: HeroRepository, private val baseRepository: BaseRepository ) : ActionService {


    override fun executeAction(action: Action): Boolean {
        if (action == Action.LOGGING) {
            val hero = heroFromSession
            if (hero != null) {
                hero.wood = hero.wood?.plus(10)
                heroRepository.save<Hero>(hero)
            }
            return true
        } else if (action == Action.MINE) {
            val hero = heroFromSession
            if (hero != null) {
                hero?.stone = hero.stone?.plus(10)
                heroRepository.save<Hero>(hero)
            }
            return true
        } else if (action == Action.CREATE_BASE) {
            val hero = heroFromSession
            if (hero?.base == null && hero?.wood!! >= 50 && hero?.stone!! >= 50) {
                val newBase = Base()
                newBase.location = hero.currentZone
                newBase.owner = hero
                newBase.stone = 0
                newBase.wood = 0
                baseRepository.save(newBase)
                hero.stone = hero.stone!! - 50
                hero.wood = hero.wood!! - 50
                heroRepository.save<Hero>(hero)
                return true
            }
        }
        return false
    }

    override fun getAvailablesActionsFromMapPoint(mapPoint: MapPoint): List<Action> {
        val terrain = mapPoint.terrain
        val actions: MutableList<Action> = LinkedList()
        if (Terrain.MOUNTAIN == terrain) {
            actions.add(Action.MINE)
        } else if (Terrain.FOREST == terrain) {
            actions.add(Action.LOGGING)
            actions.add(Action.MINE)
        } else if (Terrain.PLAIN == terrain) {
            val loggedInUser = securityService.findLoggedInUser()
            if (loggedInUser != null) {
                if (loggedInUser.hero?.base == null) {
                    actions.add(Action.CREATE_BASE)
                } else {
                    if (loggedInUser.hero?.base!!.location == mapPoint) {
                        actions.add(Action.VISIT_BASE)
                    }
                }
            }
        }
        return actions
    }

    private val heroFromSession: Hero?
        private get() {
            val findLoggedInUser = securityService.findLoggedInUser()
            if (findLoggedInUser != null) {
                return findLoggedInUser.hero
            }
            return null //TODO Maybe I should do something else here
        }
}