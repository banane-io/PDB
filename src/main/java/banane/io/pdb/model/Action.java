package banane.io.pdb.model;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public enum Action {

    MINE("MINE"),
    LOGGING("LOGGING"),
    CREATE_BASE("CREATE_BASE");

    private String name;

    private Action(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static Optional<Action> parse(@NotNull String rawAction) {
        for (Action action : Action.values()) {
            if (action.name.equalsIgnoreCase(rawAction)) {
                return Optional.of(action);
            }
        }
        return Optional.empty();
    }

}