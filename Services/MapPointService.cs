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

        public ICollection<MapPoint> LoadGrid()
        {
            int x1 = 0;
            int x2 = 5;
            int y1 = 0;
            int y2 = 5;
            return _context.MapPoints.Where(x => x.X >= x1 && x.X <= x2 && x.Y >= y1 && x.Y <= y2).OrderBy(x => x.X).ThenBy(x => x.Y).ToList();
        }
    }
}
