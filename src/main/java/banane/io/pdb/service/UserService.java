package banane.io.pdb.service;

import banane.io.pdb.model.User;

public interface UserService {
    User save(User user);

    User findByUsername(String username);
}
