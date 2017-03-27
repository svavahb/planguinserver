package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.persistence.entities.Group;
import project.persistence.entities.User;
import project.service.SearchService;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by halld on 02-Nov-16.
 */

@RestController
public class SearchController {

    // Class for sending over user info + info on whether the user is the logged in user's friend
    public class UserHolder {
        User user;
        boolean friendship;

        public User getUser() {
            return user;
        }

        public boolean isFriendship() {
            return friendship;
        }
    }

    // Service for search business logic
    SearchService searchService;

    // Constructor
    @Autowired
    public SearchController(SearchService searchService){
        this.searchService = searchService;
    }

    // Get base list of all users
    @RequestMapping(value="/search/{loggedInUser}")
    public List<UserHolder> viewGetListOfUsers(@PathVariable String loggedInUser){

        // Get logged in user and info
        User user = searchService.findByName(loggedInUser);
        List<User> users = searchService.findAll();
        List<UserHolder> userHolders = new ArrayList<>();

        // Generate user holders for all users
        for(User u:users ) {
            UserHolder uh = new UserHolder();
            uh.user = u;
            uh.friendship = searchService.checkIfFriend(u, user);
            userHolders.add(uh);
        }

        return userHolders;
    }

    // Method for searching for a certain username
    @RequestMapping(value="/search/{loggedInUser}/{searchString}")
    public List<UserHolder> getSearchByName(@PathVariable String loggedInUser, @PathVariable String searchString){

        // Get logged in user and info
        User user = searchService.findByName(loggedInUser);

        //Find user searched for
        User searchUser = searchService.findByName(searchString);
        if(user.getUsername() == null) {
            return new ArrayList<>();
        }

        // Generate necessary variables for model
        boolean friendship = searchService.checkIfFriend(searchUser, user);
        List<UserHolder> users = new ArrayList<>();
        UserHolder userHolderItem  = new UserHolder();
        userHolderItem.user = searchUser;
        userHolderItem.friendship = friendship;
        users.add(userHolderItem);

        return users;
    }

    // Post method for adding a user as the logged in user's friend
    @RequestMapping(value="/addFriend/{loggedInUser}/{userToAdd}", method = RequestMethod.POST)
    public ResponseEntity addFriendPost(@PathVariable String loggedInUser, @PathVariable int userToAdd) {
        // Find logged in user and info
        User user1 = searchService.findByName(loggedInUser);

        // Find user selected for adding as friend
        User user2 = searchService.findByUserId(userToAdd);

        // If they are not already friends, create the friendship
        if(!searchService.checkIfFriend(user1, user2)) {
            searchService.createFriendship(user1.getUserId(), user2.getUserId());
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    // Add a user to a certain group
    @RequestMapping(value="/addToGroup/{grpId}/{userId}", method = RequestMethod.POST)
    public ResponseEntity addToGroup(@PathVariable int grpId, @PathVariable int userId) {
        // Find the group and the user
        Group group = searchService.findGroup(grpId);
        User user = searchService.findByUserId(userId);

        // Add the user to the group
        searchService.addGroupMemeber(grpId, userId);
        group.addMember(user.getUsername());
        
        return new ResponseEntity(HttpStatus.OK);
    }
}
