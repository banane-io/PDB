﻿using PDB.Models;

namespace PDB.Services
{
    public interface IMapPointService
    {
        MapPoint? GetMapPoint(int id);
    }
}
