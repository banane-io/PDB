package banane.io.pdb.service;

import banane.io.pdb.model.Direction;
import banane.io.pdb.model.MapPoint;
import banane.io.pdb.model.Hero;

import java.util.List;
import java.util.Map;

public interface MapPointService {
    /**
     * Load from the database a grid of point base on a center point. This method will center the grid if possible, but if it can't
     * it will "padd" the grid with points to always have a complete grid
     */
    List<List<MapPoint>> loadGrid(MapPoint origin);

    /**
     * Load the central point and all the points around the center.
     */
    Map<Direction, MapPoint> neighbors(MapPoint origin);

    /**
     * Change the MapPoint on which the Hero is located.
     */
    void movePlayer(Hero hero, MapPoint newPosition);
}
