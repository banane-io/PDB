package banane.io.pdb.service

import banane.io.pdb.model.*
import banane.io.pdb.repository.BaseRepository
import banane.io.pdb.repository.HeroRepository
import banane.io.pdb.security.SecurityService
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

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

    private fun createMapPointWith(terrain: Terrain): MapPoint {
        return MapPoint(1, 1, 1, "This is a description", terrain)
    }

    @Test
    fun `Verify that Mountain MapPoint should return Mine action`() {
        val mountainPoint = createMapPointWith(Terrain.MOUNTAIN)

        val availableActionsFromMapPoint = serviceToTest.getAvailablesActionsFromMapPoint(mountainPoint)

        assertEquals(1, availableActionsFromMapPoint.size, "There's should only be one action available")
        assert(availableActionsFromMapPoint.contains(Action.MINE))
    }

    @Test
    fun `Verify that Forest MapPoint should return both Logging and Mining actions`() {
        val forestMapPoint = createMapPointWith(Terrain.FOREST)

        val availableActionsFromMapPoint = serviceToTest.getAvailablesActionsFromMapPoint(forestMapPoint)

        assertEquals(2, availableActionsFromMapPoint.size, "There's should only be two actions availables")
        assert(availableActionsFromMapPoint.contains(Action.MINE))
        assert(availableActionsFromMapPoint.contains(Action.LOGGING))
    }

    @Test
    fun `Verify that on Plain MapPoint when User is null should return no action`() {
        every { securityService.findLoggedInUser()} returns null

        val plainMapPoint = createMapPointWith(Terrain.PLAIN)

        val availableActionsFromMapPoint = serviceToTest.getAvailablesActionsFromMapPoint(plainMapPoint)
        assert(availableActionsFromMapPoint.isEmpty())
    }

    @Test
    fun `Verify that Create base action is return if user has a hero but no base`() {
        var user = User()
        user.hero = Hero()
        user.hero?.base = null
        every { securityService.findLoggedInUser()} returns user

        val plainMapPoint = createMapPointWith(Terrain.PLAIN)

        val availableActionsFromMapPoint = serviceToTest.getAvailablesActionsFromMapPoint(plainMapPoint)
        assertEquals(1, availableActionsFromMapPoint.size, "There's should only be one action available")
        assert(availableActionsFromMapPoint.contains(Action.CREATE_BASE))
    }

    @Test
    fun `Verify that User with a base but on different MapPoint return no action`() {
        var user = User()
        user.hero = Hero()
        var baseOfUser = Base()
        var otherMapPoint = createMapPointWith(Terrain.PLAIN)
        otherMapPoint.x = 2
        baseOfUser.location = otherMapPoint
        user.hero?.base = baseOfUser
        every { securityService.findLoggedInUser()} returns user

        val plainMapPoint = createMapPointWith(Terrain.PLAIN)

        val availableActionsFromMapPoint = serviceToTest.getAvailablesActionsFromMapPoint(plainMapPoint)
        assert(availableActionsFromMapPoint.isEmpty())
    }

    @Test
    fun `Verify that User with a base on same MapPoint return VisitBase action`() {
        var user = User()
        user.hero = Hero()
        var baseOfUser = Base()
        var sameMapPoint = createMapPointWith(Terrain.PLAIN)
        baseOfUser.location = sameMapPoint
        user.hero?.base = baseOfUser
        every { securityService.findLoggedInUser()} returns user

        val plainMapPoint = createMapPointWith(Terrain.PLAIN)

        val availableActionsFromMapPoint = serviceToTest.getAvailablesActionsFromMapPoint(plainMapPoint)
        assertEquals(1, availableActionsFromMapPoint.size, "There's should only be one action available")
        assert(availableActionsFromMapPoint.contains(Action.VISIT_BASE))
    }

    @Test
    fun `Execute Action return false if the action is not present in the else if`() {
        assertFalse(serviceToTest.executeAction(Action.VISIT_BASE))
    }

    @Test
    fun `Executing logging action returns false if there is no hero`() {
        var user = User()
        every { securityService.findLoggedInUser()} returns user

        assertFalse(serviceToTest.executeAction(Action.LOGGING))
    }

    @Test
    fun `Executing logging action add 10 wood to the hero`() {
        var user = User()
        user.hero = Hero()
        user.hero?.wood = 0
        every { securityService.findLoggedInUser()} returns user

        val slot = slot<Hero>()
        every { heroRepository.save<Hero>(capture(slot)) } answers { slot.captured }

        assert(serviceToTest.executeAction(Action.LOGGING))
        assertEquals(10, slot.captured.wood, "Hero should have 10 wood in his inventory")
    }

    @Test
    fun `Executing mining action add 10 stone to the hero`() {
        var user = User()
        user.hero = Hero()
        user.hero?.stone = 0
        every { securityService.findLoggedInUser()} returns user

        val slot = slot<Hero>()
        every { heroRepository.save<Hero>(capture(slot)) } answers { slot.captured }

        assert(serviceToTest.executeAction(Action.MINE))
        assertEquals(10, slot.captured.stone, "Hero should have 10 wood in his inventory")
    }

    @Test
    fun `Executing mining action returns false if there is no hero`() {
        var user = User()
        every { securityService.findLoggedInUser()} returns user

        assertFalse(serviceToTest.executeAction(Action.MINE))
    }

    @Test
    fun `Executing CREATE_BASE action returns false if there is no hero`() {
        var user = User()
        every { securityService.findLoggedInUser()} returns user

        assertFalse(serviceToTest.executeAction(Action.CREATE_BASE))
    }

    @Test
    fun `Executing CREATE_BASE action returns false if there a base already exist`() {
        var user = User()
        user.hero = Hero()
        user.hero?.base = Base()
        every { securityService.findLoggedInUser()} returns user

        assertFalse(serviceToTest.executeAction(Action.CREATE_BASE))
    }

    @Test
    fun `Executing CREATE_BASE action returns false if the hero doesn't have enough wood`() {
        var user = User()
        user.hero = Hero()
        user.hero?.wood = 0
        user.hero?.stone = 50
        every { securityService.findLoggedInUser()} returns user

        assertFalse(serviceToTest.executeAction(Action.CREATE_BASE))
    }

    @Test
    fun `Executing CREATE_BASE action returns false if the hero doesn't have enough stone`() {
        var user = User()
        user.hero = Hero()
        user.hero?.wood = 50
        user.hero?.stone = 0
        every { securityService.findLoggedInUser()} returns user

        assertFalse(serviceToTest.executeAction(Action.CREATE_BASE))
    }

    @Test
    fun `Executing CREATE_BASE action returns true if there the hero have enough resources`() {
        var user = User()
        user.hero = Hero()
        user.hero?.wood = 50
        user.hero?.stone = 50
        user.hero?.currentZone = createMapPointWith(Terrain.PLAIN)
        every { securityService.findLoggedInUser()} returns user

        val slot = slot<Hero>()
        every { heroRepository.save<Hero>(capture(slot)) } answers { slot.captured }

        val slotBase = slot<Base>()
        every { baseRepository.save(capture(slotBase))} answers { slotBase.captured }

        assert(serviceToTest.executeAction(Action.CREATE_BASE))

        val hero = slot.captured
        assertEquals(0, hero.wood, "Hero has no wood anymore")
        assertEquals(0, hero.stone, "Hero has no stone anymore")

        val newBase = slotBase.captured

        assertEquals(0, newBase.stone, "Initial value of stone")
        assertEquals(0, newBase.wood, "Initial value of wood")
        assertEquals(user.hero?.currentZone, newBase.location, "Location should be at the currentZone of Hero")
        assertEquals(user.hero, newBase.owner, "The owner of the base is the hero of the user")
    }
}
