﻿using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using PDB.Models;
using PDB.Services;

namespace PDB.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class MapController : ControllerBase
    {
        private readonly IMapPointService _mapPointService;

        public MapController(IMapPointService mapPointService)
        {
            _mapPointService = mapPointService;
        }

        [HttpGet("{id:int}")]
        public ActionResult<MapPoint> GetZone(int id)
        {
            MapPoint? mapPoint = _mapPointService.GetMapPoint(id);
            if (mapPoint == null)
                return NotFound();
            return Ok(mapPoint);
        }
    }
}
