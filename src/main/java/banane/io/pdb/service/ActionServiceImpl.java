package banane.io.pdb.service;

import banane.io.pdb.model.Base;
import banane.io.pdb.repository.BaseRepository;
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
            if(hero.getWood() >= 50 && hero.getStone() >= 50) {
                Base newBase = new Base();
                newBase.setLocation(hero.getCurrentZone());
                newBase.setOwner(hero);

                baseRepository.save(newBase);
                return true;
            }
        }
        return false;
    }

    private Hero getHeroFromSession() {
        User findLoggedInUser = securityService.findLoggedInUser();
        return findLoggedInUser.getHero();
    }

}