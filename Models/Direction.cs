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

    public static readonly Direction NorthWest = new(-1, 1);
    public static readonly Direction North = new(0, 1);
    public static readonly Direction NorthEast = new(1, 1);
    public static readonly Direction East = new(1, 0);
    public static readonly Direction SouthEast = new(1, -1);
    public static readonly Direction South = new(0, -1);
    public static readonly Direction SouthWest = new(-1, -1);
    public static readonly Direction West = new(-1, 0);

    public static readonly IList<Direction> AllDirections = new List<Direction>()
    {
        NorthWest, North, NorthEast, East, South, SouthEast, SouthWest, West
    };
}