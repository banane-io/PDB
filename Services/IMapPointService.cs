using PDB.Models;

namespace PDB.Services
{
    public interface IMapPointService
    {
        Task<MapPoint?> GetMapPoint(long id);
        Task<IEnumerable<IEnumerable<MapPoint>>> LoadGrid(MapPoint center);
        Task<IDictionary<Direction, MapPoint>> Neighbors(MapPoint center);
    }
}
