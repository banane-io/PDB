package banane.io.pdb.service;

import banane.io.pdb.model.Direction;
import banane.io.pdb.model.MapPoint;
import banane.io.pdb.model.Player;

import java.util.List;
import java.util.Map;

public interface MapPointService {
    List<MapPoint> loadGrid(MapPoint origin);

    Map<Direction, MapPoint> neighbors(MapPoint origin);

    void movePlayer(Player player, MapPoint newPosition);
}
