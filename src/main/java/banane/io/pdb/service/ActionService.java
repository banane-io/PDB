package banane.io.pdb.service;

import banane.io.pdb.model.Action;
import banane.io.pdb.model.MapPoint;

import java.util.List;

public interface ActionService {
    
    boolean executeAction(Action action);

    List<String> getAvailablesActionsFromMapPoint(MapPoint mapPoint);
}