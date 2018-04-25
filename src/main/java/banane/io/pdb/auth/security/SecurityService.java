package banane.io.pdb.auth.security;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}
