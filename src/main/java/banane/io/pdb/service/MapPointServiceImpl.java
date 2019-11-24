package banane.io.pdb.service;

import banane.io.pdb.model.Direction;
import banane.io.pdb.model.MapPoint;
import banane.io.pdb.model.Hero;
import banane.io.pdb.repository.MapPointRepository;
import banane.io.pdb.repository.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class MapPointServiceImpl implements MapPointService {

    @Autowired
    private MapPointRepository mapPointRepository;

    @Autowired
    private HeroRepository heroRepository;

    private final int X_MINIMUM_VALUE = 0;
    private final int Y_MINIMUM_VALUE = 0;
    private final int X_MAXIMUM_VALUE = 25;
    private final int Y_MAXIMUM_VALUE = 25;
    private final int GRID_RADIUS = 5;

    @Override
    public List<List<MapPoint>> loadGrid(MapPoint origin) {
        int[] xValues = calculateMinMax(GRID_RADIUS, X_MINIMUM_VALUE, X_MAXIMUM_VALUE, origin.getX());
        int[] yValues = calculateMinMax(GRID_RADIUS, Y_MINIMUM_VALUE, Y_MAXIMUM_VALUE, origin.getY());
        
        final List<MapPoint> mapPoints = mapPointRepository.loadGrid(xValues[0], xValues[1], yValues[0], yValues[1]);
        final List<List<MapPoint>> mapPointsForGrid = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            List<MapPoint> row = new ArrayList<>();
            for (int j = 0; j < 11; j++) {
                row.add(mapPoints.get((i * 11) + j));
            }
            mapPointsForGrid.add(row);
        }
        return mapPointsForGrid;
    }

    @Override
    public Map<Direction, MapPoint> neighbors(MapPoint origin) {
        Map<Direction, MapPoint> neighbors = new HashMap<>();
        for (Direction direction : Direction.values()) {
            Optional<MapPoint> result = mapPointRepository.findMapPointByXAndY(origin.getX() + direction.getDx(), origin.getY() + direction.getDy());
            if(result.isPresent()) {
                neighbors.put(direction, result.get());
            }
        }
        return neighbors;
    }

    @Override
    public void movePlayer(Hero hero, MapPoint newPosition) {
        hero.setCurrentZone(newPosition);
        heroRepository.save(hero);
    }

    private int[] calculateMinMax(int rangeMap, int minMap, int maxMap, int position) {
        int[] minMax = new int[2];

        int overflow = 0;
        int underflow = 0;
        int borderLeft = position - rangeMap;
        int borderRight = position + rangeMap;

        if (borderLeft < minMap) {
            overflow = Math.abs(borderLeft);
        }

        if (borderRight > maxMap) {
            underflow = borderRight - maxMap;
        }

        int min = Stream.of(minMap, borderLeft).mapToInt(v -> v).max().getAsInt();
        min = min - underflow;
        int max = Stream.of(maxMap, borderRight).mapToInt(v -> v).min().getAsInt();
        max = max + overflow;
        minMax[0] = min;
        minMax[1] = max;
        return minMax;
    }
}
