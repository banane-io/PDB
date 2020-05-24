package banane.io.pdb.service

import banane.io.pdb.model.Action
import banane.io.pdb.model.MapPoint
import banane.io.pdb.model.Terrain
import org.junit.Test
import kotlin.test.assertEquals

internal class ActionServiceImplTest {
    private val serviceToTest = ActionServiceImpl()

    @Test
    fun `Verify that Mountains return Mine action`() {
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
    fun `Verify that Forest return both Logging and Minning actions`() {
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
}
