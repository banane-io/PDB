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
import org.junit.Test
import kotlin.test.assertEquals

internal class ActionServiceImplTest {

    @Test
    fun `Verify that Mountains return Mine action`() {
        val securityService = mockk<SecurityService>()
        val heroRepository = mockk<HeroRepository>()
        val baseRepository = mockk<BaseRepository>()

        val serviceToTest = ActionServiceImpl(securityService, heroRepository, baseRepository)

        val mountainPoint = MapPoint()
        mountainPoint.id = 1;
        mountainPoint.terrain = Terrain.MOUNTAIN
        mountainPoint.x = 1
        mountainPoint.y = 1
        mountainPoint.zone = "This is a description"
        val availableActionsFromMapPoint = serviceToTest.getAvailablesActionsFromMapPoint(mountainPoint)
        assertEquals(1, availableActionsFromMapPoint.size, "There's should only be one action available")
        assert(availableActionsFromMapPoint.contains(Action.MINE))
    }

    @Test
    fun `Verify that Forest return both Logging and Miening actions`() {
        val securityService = mockk<SecurityService>()
        val heroRepository = mockk<HeroRepository>()
        val baseRepository = mockk<BaseRepository>()

        val serviceToTest = ActionServiceImpl(securityService, heroRepository, baseRepository)
        val forestMapPoint = MapPoint()
        forestMapPoint.id = 1;
        forestMapPoint.terrain = Terrain.FOREST
        forestMapPoint.x = 1
        forestMapPoint.y = 1
        forestMapPoint.zone = "This is a description"
        val availableActionsFromMapPoint = serviceToTest.getAvailablesActionsFromMapPoint(forestMapPoint)
        assertEquals(2, availableActionsFromMapPoint.size, "There's should only be two actions availables")
        assert(availableActionsFromMapPoint.contains(Action.MINE))
        assert(availableActionsFromMapPoint.contains(Action.LOGGING))
    }

    @Test
    fun `No action on plains when User is null`() {
        val securityService = mockk<SecurityService>()
        every { securityService.findLoggedInUser()} returns null
        val heroRepository = mockk<HeroRepository>()
        val baseRepository = mockk<BaseRepository>()

        val serviceToTest = ActionServiceImpl(securityService, heroRepository, baseRepository)

        val plainMapPoint = MapPoint()
        plainMapPoint.id = 1;
        plainMapPoint.terrain = Terrain.PLAIN
        plainMapPoint.x = 1
        plainMapPoint.y = 1
        plainMapPoint.zone = "This is a description"

        val availableActionsFromMapPoint = serviceToTest.getAvailablesActionsFromMapPoint(plainMapPoint)
        assert(availableActionsFromMapPoint.isEmpty())
    }
}
