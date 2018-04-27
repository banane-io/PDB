package banane.io.pdb.security;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}
