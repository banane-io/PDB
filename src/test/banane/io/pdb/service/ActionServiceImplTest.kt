package banane.io.pdb.service

import banane.io.pdb.model.Action
import banane.io.pdb.model.MapPoint
import banane.io.pdb.model.Terrain
import banane.io.pdb.model.User
import banane.io.pdb.repository.BaseRepository
import banane.io.pdb.repository.HeroRepository
import banane.io.pdb.security.SecurityService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ActionServiceImplTest {

    lateinit var serviceToTest: ActionServiceImpl
    lateinit var securityService: SecurityService
    lateinit var heroRepository: HeroRepository
    lateinit var baseRepository: BaseRepository

    @BeforeEach
    fun setup() {
        securityService = mockk<SecurityService>()
        heroRepository = mockk<HeroRepository>()
        baseRepository = mockk<BaseRepository>()

        serviceToTest = ActionServiceImpl(securityService, heroRepository, baseRepository)
    }

    fun createMapPointWith(terrain: Terrain): MapPoint {
        var mapPoint = MapPoint()
        mapPoint.id = 1;
        mapPoint.terrain = terrain
        mapPoint.x = 1
        mapPoint.y = 1
        mapPoint.zone = "This is a description"
        return mapPoint
    }

    @Test
    fun `Verify that Mountains return Mine action`() {
        val mountainPoint = createMapPointWith(Terrain.MOUNTAIN)

        val availableActionsFromMapPoint = serviceToTest.getAvailablesActionsFromMapPoint(mountainPoint)

        assertEquals(1, availableActionsFromMapPoint.size, "There's should only be one action available")
        assert(availableActionsFromMapPoint.contains(Action.MINE))
    }

    @Test
    fun `Verify that Forest return both Logging and Miening actions`() {
        val forestMapPoint = createMapPointWith(Terrain.FOREST)

        val availableActionsFromMapPoint = serviceToTest.getAvailablesActionsFromMapPoint(forestMapPoint)

        assertEquals(2, availableActionsFromMapPoint.size, "There's should only be two actions availables")
        assert(availableActionsFromMapPoint.contains(Action.MINE))
        assert(availableActionsFromMapPoint.contains(Action.LOGGING))
    }

    @Test
    fun `No action on plains when User is null`() {
        every { securityService.findLoggedInUser()} returns null

        val plainMapPoint = createMapPointWith(Terrain.PLAIN)
        
        val availableActionsFromMapPoint = serviceToTest.getAvailablesActionsFromMapPoint(plainMapPoint)
        assert(availableActionsFromMapPoint.isEmpty())
    }
}
