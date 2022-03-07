namespace PDB.Models;

public class Direction
{
    public int DeltaX { get; }
    public int DeltaY { get; }

    private Direction(int deltaX, int deltaY)
    {
        DeltaX = deltaX;
        DeltaY = deltaY;
    }

    public static readonly Direction NorthWest = new Direction(-1, 1);
    public static readonly Direction North = new Direction(0, 1);
    public static readonly Direction NorthEast = new Direction(1, 1);
    public static readonly Direction East = new Direction(1, 0);
    public static readonly Direction SouthEast = new Direction(1, -1);
    public static readonly Direction South = new Direction(0, -1);
    public static readonly Direction SouthWest = new Direction(-1, -1);
    public static readonly Direction West = new Direction(-1, 0);
}