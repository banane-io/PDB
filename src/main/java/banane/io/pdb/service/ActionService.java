package banane.io.pdb.service;

import banane.io.pdb.model.Action;

public interface ActionService {
    
    boolean executeAction(Action action);
}