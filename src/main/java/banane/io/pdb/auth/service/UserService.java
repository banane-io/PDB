package banane.io.pdb.auth.service;

import banane.io.pdb.auth.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
