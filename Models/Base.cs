using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace PDB.Models;

public class Base
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public long Id { get; set; }
    public MapPoint Location { get; set; }
    public int Wood { get; set; }
    public int Stone { get; set; }
    public Hero Owner { get; set; }
}