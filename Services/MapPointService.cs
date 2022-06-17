using PDB.Models;

namespace PDB.Services
{
    public class MapPointService : IMapPointService
    {
        private readonly ApplicationContext _context;

        public MapPointService(ApplicationContext context)
        {
            _context = context;
        }

        public MapPoint? GetMapPoint(long id)
        {
            MapPoint? mapPoint = _context.Find<MapPoint>(id);
            
            return mapPoint;
        }
    }
}
