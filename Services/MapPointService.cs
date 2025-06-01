using Microsoft.EntityFrameworkCore;
using PDB.Models;

namespace PDB.Services
{
    public class MapPointService : IMapPointService
    {
        private readonly ApplicationContext _context;
        //Those are constant for that map that will be variable at some point.
        private const int XMinimumValue = 0;
        private const int YMinimumValue = 0;
        private const int XMaximumValue = 24;
        private const int YMaximumValue = 24;
        private const int GridRange = 5;

        public MapPointService(ApplicationContext context)
        {
            _context = context;
        }

        public async Task<MapPoint?> GetMapPoint(long id)
        {
            MapPoint? mapPoint = await _context.FindAsync<MapPoint>(id);

            return mapPoint;
        }

        public async Task<IEnumerable<IEnumerable<MapPoint>>> LoadGrid(MapPoint center)
        {
            (int x1, int x2) = CalculateMinMax(GridRange, XMinimumValue, XMaximumValue, center.X);
            (int y1, int y2) = CalculateMinMax(GridRange, YMinimumValue, YMaximumValue, center.Y);

            var grid = await _context.MapPoints.Where(x => x.X >= x1 && x.X <= x2 && x.Y >= y1 && x.Y <= y2).OrderBy(x => x.X).ThenBy(x => x.Y).ToListAsync();
            return grid.GroupBy(x => x.X);
        }

        private (int, int) CalculateMinMax(int radius, int minMap, int maxMap, int center)
        {
            var overflow = 0;
            var underflow = 0;
            var borderLeft = center - radius;
            var borderRight = center + radius;

            if (borderLeft < minMap)
            {
                overflow = Math.Abs(borderLeft); //This works only because the min is 0, otherwise I would have to calculate it
            }

            if (borderRight > maxMap)
            {
                underflow = borderRight - maxMap;
            }

            var absMin = Math.Max(borderLeft, minMap);
            absMin -= underflow;

            var absMax = Math.Min(borderRight, maxMap);
            absMax += overflow;

            return (absMin, absMax);
        }

        public async Task<List<(Direction, MapPoint)>> Neighbors(MapPoint center)
        {
            var neighbors = new List<(Direction, MapPoint)>();

            foreach(var direction in Direction.AllDirections)
            {
                var mapPoint = await _context.MapPoints.Where(point => point.X == center.X + direction.DeltaX && point.Y == center.Y + direction.DeltaY).SingleOrDefaultAsync();
                if(mapPoint != null)
                {
                    neighbors.Add(new (direction, mapPoint));
                }
            }

            return neighbors;
        }
    }
}
