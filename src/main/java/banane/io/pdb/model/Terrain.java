package banane.io.pdb.model;

public enum Terrain {
    PLAIN("green"),
    SWAMP("DarkGreen"),
    BEACH("yellow"),
    MOUNTAIN("gray"),
    WATER("blue"),
    BRIDGE("SaddleBrown");

    private String colour;

    private Terrain(String colour) {
        this.colour = colour;
    }
}
