package banane.io.pdb.service;

import banane.io.pdb.model.MapPoint;

import java.util.List;

public interface MapPointService {
    List<MapPoint> loadGrid(MapPoint origin);
}
