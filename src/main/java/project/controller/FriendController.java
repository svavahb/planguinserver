package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.persistence.entities.Schedule;
import project.persistence.entities.User;
import project.service.ScheduleService;
import project.service.SearchService;

import java.util.List;

/**
 * Created by Svava on 19.03.17.
 */

@RestController
public class FriendController {

    private SearchService searchService;
    private ScheduleService scheduleService;

    // Constructor
    @Autowired
    public FriendController(SearchService searchService, ScheduleService scheduleService){
        this.searchService = searchService;
        this.scheduleService = scheduleService;
    }

    // Method for getting all friends of a user
    @RequestMapping(value="getFriends/{loggedInUser}")
    public List<String> getFriends(@PathVariable String loggedInUser) {
        // Get logged in user
        User user = scheduleService.findUserByUsername(loggedInUser);

        return user.getFriends();
    }

    // Method for removing a user from the logged in user's friend list
    @RequestMapping(value="/deleteFriendship/{loggedInUser}/{friendName}")
    public ResponseEntity deleteFriendship(@PathVariable String loggedInUser, @PathVariable String friendName) {
        // Get logged in user
        User user = scheduleService.findUserByUsername(loggedInUser);
        User friend = scheduleService.findUserByUsername(friendName);

        // Delete the friendship
        scheduleService.deleteFriendship(user.getUserId(), friend.getUserId());
        return new ResponseEntity(HttpStatus.OK);
    }

}
