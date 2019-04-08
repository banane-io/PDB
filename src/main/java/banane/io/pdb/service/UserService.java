package banane.io.pdb.service;

import java.util.Optional;

import banane.io.pdb.model.User;

public interface UserService {
    User save(User user);

    Optional<User> findByUsername(String username);
}
