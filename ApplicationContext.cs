using Microsoft.EntityFrameworkCore;

namespace PDB
{
    public class ApplicationContext : DbContext
    {
        public ApplicationContext(DbContextOptions options)
            : base(options)
        {
        }
    }
}
