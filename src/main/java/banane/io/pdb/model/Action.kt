package banane.io.pdb.model

import java.util.*
import javax.validation.constraints.NotNull

enum class Action(val nameOfAction: String) {
    MINE("MINE"), LOGGING("LOGGING"), CREATE_BASE("CREATE_BASE"), VISIT_BASE("VISIT_BASE");

    companion object {
        fun parse(rawAction: @NotNull String?): Optional<Action> {
            for (action in values()) {
                if (action.nameOfAction.equals(rawAction, ignoreCase = true)) {
                    return Optional.of(action)
                }
            }
            return Optional.empty()
        }
    }

}