using PDB.Models;

namespace PDB.Services
{
    public class MapPointService : IMapPointService
    {
        public MapPoint? GetMapPoint(int id)
        {
           /* using var context = new ApplicationContext();
            MapPoint? mapPoint = context.Find<MapPoint>(id);*/
            
            return new MapPoint();
        }
    }
}
