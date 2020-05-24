package banane.io.pdb.service

import banane.io.pdb.model.Action
import banane.io.pdb.model.MapPoint
import banane.io.pdb.model.Terrain
import org.junit.Test

internal class ActionServiceImplTest {
    private val serviceToTest = ActionServiceImpl()

    @Test
    fun `Verify that Mountains return Mine cation`() {
        val mountainPoint = MapPoint()
        mountainPoint.id = 1;
        mountainPoint.terrain = Terrain.MOUNTAIN
        mountainPoint.x = 1
        mountainPoint.y = 1
        mountainPoint.zone = "This is a description"
        val availableActionsFromMapPoint = serviceToTest.getAvailablesActionsFromMapPoint(mountainPoint)
        assert(availableActionsFromMapPoint.contains(Action.MINE))
    }
}
