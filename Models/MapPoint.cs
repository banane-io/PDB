using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace PDB.Models;

public class MapPoint
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public long Id { get; set; }
    public int X { get; set; }
    public int Y { get; set; }
    public string Zone { get; set; }
    public Terrain Terrain { get; set; }
}