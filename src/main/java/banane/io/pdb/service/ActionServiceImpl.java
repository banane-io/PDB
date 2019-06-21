package banane.io.pdb.service;

import banane.io.pdb.model.*;
import banane.io.pdb.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import banane.io.pdb.repository.HeroRepository;
import banane.io.pdb.security.SecurityService;

import java.util.LinkedList;
import java.util.List;

@Service
public class ActionServiceImpl implements ActionService {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private HeroRepository heroRepository;

    @Autowired
    private BaseRepository baseRepository;

    @Override
    public boolean executeAction(Action action) {
        if (action.equals(Action.LOGGING)) {
            Hero hero = getHeroFromSession();
            hero.setWood(hero.getWood() + 10);
            heroRepository.save(hero);
            return true;
        } else if (action.equals(Action.MINE)) {
            Hero hero = getHeroFromSession();
            hero.setStone(hero.getStone() + 10);
            heroRepository.save(hero);
            return true;
        } else if (action.equals(Action.CREATE_BASE)) {
            Hero hero = getHeroFromSession();
            if(hero.getBase() == null && hero.getWood() >= 50 && hero.getStone() >= 50) {
                Base newBase = new Base();
                newBase.setLocation(hero.getCurrentZone());
                newBase.setOwner(hero);

                baseRepository.save(newBase);
                hero.setStone(hero.getStone() - 50);
                hero.setWood(hero.getWood() - 50);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> getAvailablesActionsFromMapPoint(MapPoint mapPoint) {
        Terrain terrain = mapPoint.getTerrain();
        List<String> actions = new LinkedList<>();
        if (Terrain.MOUNTAIN.equals(terrain)) {
            actions.add(Action.MINE.getName());
        } else if (Terrain.FOREST.equals(terrain)) {
            actions.add(Action.LOGGING.getName());
            actions.add(Action.MINE.getName());
        } else if (Terrain.PLAIN.equals(terrain)) {
            User loggedInUser = securityService.findLoggedInUser();
            if(loggedInUser.getHero().getBase() == null ) {
                actions.add(Action.CREATE_BASE.getName());
            } else if(loggedInUser.getHero().getBase().getLocation().equals(mapPoint)) {
                actions.add(Action.VISIT_BASE.getName());
            }
        }
        return actions;
    }

    private Hero getHeroFromSession() {
        User findLoggedInUser = securityService.findLoggedInUser();
        return findLoggedInUser.getHero();
    }

}