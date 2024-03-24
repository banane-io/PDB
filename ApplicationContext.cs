using Microsoft.EntityFrameworkCore;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.AspNetCore.Identity;
using PDB.Models;

namespace PDB
{
    public class ApplicationContext : IdentityDbContext<IdentityUser>
    {
        public ApplicationContext(DbContextOptions options)
            : base(options)
        {
        }

        public DbSet<MapPoint> MapPoints { get; set; }
        public DbSet<Hero> Heroes { get; set; }
        public DbSet<Base> Bases { get; set; }
    }
}
