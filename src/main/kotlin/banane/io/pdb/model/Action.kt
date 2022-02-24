package banane.io.pdb.model

import java.util.*

enum class Action(val nameOfAction: String) {
    MINE("MINE"), LOGGING("LOGGING"), CREATE_BASE("CREATE_BASE"), VISIT_BASE("VISIT_BASE");

    companion object {
        fun parse(rawAction: String?): Optional<Action> {
            for (action in values()) {
                if (action.nameOfAction.equals(rawAction, ignoreCase = true)) {
                    return Optional.of(action)
                }
            }
            return Optional.empty()
        }
    }

}