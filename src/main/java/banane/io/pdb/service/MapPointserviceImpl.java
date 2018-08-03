package banane.io.pdb.service;

import banane.io.pdb.model.MapPoint;
import banane.io.pdb.repository.MapPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class MapPointserviceImpl implements MapPointService {

    @Autowired
    private MapPointRepository mapPointRepository;

    @Override
    public List<MapPoint> loadGrid(MapPoint origin) {
        int[] xValues = calculateMinMax(5, 0, 25, origin.getX());
        int[] yValues = calculateMinMax(5, 0, 25, origin.getY());
        return mapPointRepository.loadGrid(xValues[0], xValues[1], yValues[0], yValues[1]);
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
