package banane.io.pdb.service

import banane.io.pdb.model.Direction
import banane.io.pdb.model.Hero
import banane.io.pdb.model.MapPoint

interface MapPointService {
    /**
     * Load from the database a grid of point base on a center point. This method will center the grid if possible, but if it can't
     * it will "padd" the grid with points to always have a complete grid
     */
    fun loadGrid(origin: MapPoint): List<List<MapPoint?>>

    /**
     * Load the central point and all the points around the center.
     */
    fun neighbors(origin: MapPoint): Map<Direction, MapPoint>

    /**
     * Change the MapPoint on which the Hero is located.
     */
    fun movePlayer(hero: Hero?, newPosition: MapPoint?)
}