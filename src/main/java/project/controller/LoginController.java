package project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.persistence.entities.User;
import project.service.LoginService;
import project.service.SearchService;
import project.service.SecurityService;
import project.validator.UserValidator;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by halld on 02-Nov-16.
 */

@RestController
public class LoginController {

    // Main service for log in business logic
    LoginService loginService;
    SearchService searchService;

    // Service for security
    @Autowired
    SecurityService securityService = new SecurityService();

    @Autowired
    public LoginController(LoginService loginService, SearchService searchService){
        this.loginService = loginService;
        this.searchService = searchService;
    }

    // Post method for signing up user
    @PostMapping(value="/signup")
    public User signUpPost(@RequestBody User SignUp) {

        // Create user in database
        User user = loginService.createUser(SignUp.getUsername(),SignUp.getPassword(), SignUp.getPhoto(), SignUp.getSchool());
        return user;
    }

    // Post method for logging in user
    @PostMapping(value="/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public User LogInPost(@RequestBody User LogIn) {
        // Attempt login, return the result
        User user = new User();
        if (securityService.autologin(LogIn.getUsername(), LogIn.getPassword())) {
            user.setUsername("true");
        }
        else user.setUsername("false");

        return user;
    }

    // Post method for checking whether a username exists
    @RequestMapping(value="/usernameExists/{username}")
    public User checkUsernamePost(@PathVariable String username) {
        User user = searchService.findByName(username);
        if (!user.getUsername().isEmpty()) {
            user.setUsername("EXISTS");
            return user;
        }
        user.setUsername("OK");
        return user;
    }

}
