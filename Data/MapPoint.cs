using System;

namespace PDB.Data;

public class MapPoint
{
    public Guid Id { get; set; }
    public int X { get; set; }
    public int Y { get; set; }
    public String Zone { get; set; }
    public Terrain Terrain { get; set; }
}