using Microsoft.EntityFrameworkCore;
using PDB.Models;

namespace PDB;

public class ApplicationContext : DbContext
{
    public ApplicationContext(DbContextOptions options)
        : base(options)
    {
    }

    public DbSet<MapPoint> MapPoints { get; set; }
    public DbSet<Hero> Heroes { get; set; }
    public DbSet<Base> Bases { get; set; }
}