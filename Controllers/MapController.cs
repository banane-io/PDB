using Microsoft.AspNetCore.Mvc;
using PDB.Models;
using PDB.Services;

namespace PDB.Controllers;

[Route("api/[controller]")]
[ApiController]
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
        var mapPoint = await _mapPointService.GetMapPoint(id);
        if (mapPoint == null)
            return NotFound();
        return Ok(mapPoint);
    }

    [HttpGet]
    public async Task<ActionResult<IList<MapPoint>>> LoadGrid()
    {
        return Ok(await _mapPointService.LoadGrid(new MapPoint { X = 0, Y = 0 }));
    }

    [HttpGet("{id:long}/neighbors")]
    public async Task<ActionResult<List<NeighborResponse>>> Neighbors(long id)
    {
        var mapPoint = await _mapPointService.GetMapPoint(id);
        if (mapPoint == null)
            return NotFound();
        var neighbors = await _mapPointService.Neighbors(mapPoint);
        return Ok(neighbors.Select(n => new NeighborResponse(n.Item1, n.Item2)).ToList());
    }
}

public record NeighborResponse(Direction Direction, MapPoint MapPoint);