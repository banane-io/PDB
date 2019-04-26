package banane.io.pdb.model;

public enum Action {

    MINE("MINE"),
    LOGGING("LOGGING");

    private String name;

    private Action(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}