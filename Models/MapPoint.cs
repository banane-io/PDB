namespace PDB.Models;

public class MapPoint
{
    public Guid Id { get; set; }
    public int X { get; set; }
    public int Y { get; set; }
    public string Zone { get; set; }
    public Terrain Terrain { get; set; }
}