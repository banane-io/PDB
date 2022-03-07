namespace PDB.Models;

public class Hero
{
    public Guid Id { get; set; }
    //public string Username { get; set; }
    //public User owner { get; set; }
    public MapPoint CurrentZone { get; set; }
    public int Strength { get; set; }
    public int Agility { get; set; }
    public int Intelligence { get; set; }
    public int Hp { get; set; }
    public int Mana { get; set; }
    public int Wood { get; set; }
    public int Stone { get; set; }
    //public Base? Base { get; set; } 
}