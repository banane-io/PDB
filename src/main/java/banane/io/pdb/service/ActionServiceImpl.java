package banane.io.pdb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import banane.io.pdb.model.Action;
import banane.io.pdb.model.Hero;
import banane.io.pdb.model.User;
import banane.io.pdb.repository.HeroRepository;
import banane.io.pdb.security.SecurityService;

@Service
public class ActionServiceImpl implements ActionService {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private HeroRepository heroRepository;

    @Override
    public boolean executeAction(Action action) {
        if (action.equals(Action.LOGGING)) {
            User findLoggedInUser = securityService.findLoggedInUser();
            Hero hero = findLoggedInUser.getHero();
            hero.setWood(hero.getWood() + 10);
            heroRepository.save(hero);
            return true;
        } else if (action.equals(Action.MINE)) {
            User findLoggedInUser = securityService.findLoggedInUser();
            Hero hero = findLoggedInUser.getHero();
            hero.setStone(hero.getStone() + 10);
            heroRepository.save(hero);
            return true;
        }
        return false;
    }
    
}