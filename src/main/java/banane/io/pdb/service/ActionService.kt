package banane.io.pdb.service

import banane.io.pdb.model.Action
import banane.io.pdb.model.MapPoint

interface ActionService {
    /**
     * Try to execute the action base on the present value of the hero/zone. Will return false if it can't execute
     * the action.
     */
    fun executeAction(action: Action): Boolean

    /**
     * Return a list of actions possbile based on the information like the position of the Hero
     * and the type of the MapPoint on which the Hero is. The list can be empty.
     */
    fun getAvailablesActionsFromMapPoint(mapPoint: MapPoint): List<Action>
}