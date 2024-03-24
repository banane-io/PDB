using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using PDB.Models;
using PDB.Services;

namespace PDB.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize]
    public class MapController : ControllerBase
    {
        private readonly IMapPointService _mapPointService;

        public MapController(IMapPointService mapPointService)
        {
            _mapPointService = mapPointService;
        }

        [HttpGet("{id:long}")]
        public async Task<ActionResult<MapPoint>> GetZone(long id)
        {
            MapPoint? mapPoint = await _mapPointService.GetMapPoint(id);
            if (mapPoint == null)
                return NotFound();
            return Ok(mapPoint);
        }

        [HttpGet]
        public async Task<ActionResult<IList<MapPoint>>> LoadGrid()
        {
            return Ok(await _mapPointService.LoadGrid(new MapPoint() { X = 0, Y = 0}));
        }

        [HttpGet("neighbors")]
        public async Task<ActionResult<IDictionary<Direction, MapPoint>>> Neighbors()
        {
            return Ok(await _mapPointService.Neighbors(new MapPoint() { X = 0, Y = 0 }));
        }
    }
}
