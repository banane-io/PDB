package banane.io.pdb.security;

import banane.io.pdb.model.User;

public interface SecurityService {
    String findLoggedInUsername();

    User findLoggedInUser();

    void autologin(String username, String password);
}
