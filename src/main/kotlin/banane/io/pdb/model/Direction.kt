package banane.io.pdb.model

enum class Direction(val dx: Int, val dy: Int) {
    NORTHWEST(-1, -1), NORTH(0, -1), NORTHEAST(1, -1), EAST(1, 0), SOUTHEAST(1, 1), SOUTH(0, 1), SOUTHWEST(-1, 1), WEST(-1, 0);

}