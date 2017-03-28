package project.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.persistence.entities.User;
import project.persistence.repositories.Repository;

/**
 * Created by Svava on 16.11.16.
 */
@Service
public class SecurityService {

    // Used for checking password
    PasswordEncoder pwEncoder = new BCryptPasswordEncoder();
    Repository repository = new Repository();

    private UserDetailsService userDetailsService = new UserDetailsServiceImpl();

    // Log in user
    public boolean autologin(String username, String password) {
        // Check if username already exists
        User user = repository.findUsersByName(username);
        if(user.getUsername().isEmpty()) {
            return false;
        }

        // Check manually whether password is correct
        if (pwEncoder.matches(password, user.getPassword())) {
            return true;
        }
        return false;
    }
}
