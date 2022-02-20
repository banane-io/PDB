using System;

namespace PDB.Data;

public class Hero
{
    public Guid Id { get; set; }
    public String Username { get; set; }
    public int Strength { get; set; }
    public int Agility { get; set; }
    public int Intelligence { get; set; }
    public int Hp { get; set; }
    public int Mana { get; set; }
    public int Wood { get; set; }
    public int Stone { get; set; }
}