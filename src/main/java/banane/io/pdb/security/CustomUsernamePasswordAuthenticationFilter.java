package banane.io.pdb.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;


public class CustomUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    Logger logger = LoggerFactory.getLogger(CustomUsernamePasswordAuthenticationFilter.class);

    public CustomUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManagerBean) {
        super(new AntPathRequestMatcher("/api/login", "POST"));
        super.setAuthenticationManager(authenticationManagerBean);
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        logger.info("Attempting authentication with CustomUsernamePasswordAuthenticationFilter");
        try {
            BufferedReader reader = request.getReader();
            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String parsedReq = sb.toString();
            if (parsedReq != null) {
                ObjectMapper mapper = new ObjectMapper();
                AuthReq authReq = mapper.readValue(parsedReq, AuthReq.class);
                logger.info("Authenticating : " + authReq.getPassword() + " | " + authReq.getPassword());
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword());
                //setDetails(request,authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InternalAuthenticationServiceException("Failed to parse authentication request body");
        }
        return null;
    }

    public static class AuthReq {
        String username;
        String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }


}
