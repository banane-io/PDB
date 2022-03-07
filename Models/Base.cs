namespace PDB.Models;

public class Base
{
    public Guid Id { get; set; }
    public MapPoint Location { get; set; }
    public int Wood { get; set; }
    public int Stone { get; set; }
    public Hero Owner { get; set; }
}