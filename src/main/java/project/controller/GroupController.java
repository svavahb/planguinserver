package project.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.persistence.entities.Date;
import project.persistence.entities.Group;
import project.persistence.entities.User;
import project.service.ScheduleService;
import project.service.SearchService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Svava on 19.03.17.
 */

@RestController
public class GroupController {

    private ScheduleService scheduleService;
    private SearchService searchService;

    // Constructor
    @Autowired
    public GroupController(ScheduleService scheduleService, SearchService searchService){
        this.scheduleService = scheduleService;
        this.searchService = searchService;
    }

    // Method for getting the groups a user is in
    @RequestMapping(value="/getGroups/{loggedInUser}")
    public List<Group> getGroups(@PathVariable String loggedInUser) {
        // Get logged in user and info
        User user = scheduleService.findUserByUsername(loggedInUser);

        List<Group> groupNames = user.getGroups();

        return groupNames;
    }

    // Method for creating a group
    @RequestMapping(value="/createGroup/{loggedInUser}/{grpName}")
    public User createGroup(@PathVariable String loggedInUser, @PathVariable String grpName) {
        // Get logged in user and info
        User user = scheduleService.findUserByUsername(loggedInUser);
        List<String> members = new ArrayList<>();
        members.add(user.getUsername());

        // If a group with the same name already exists, reload page with error
        if(!scheduleService.createGroup(grpName, user.getUserId())) {
            user.setUsername("false");
            return user;
        }

        user.addGroup(searchService.findGroup(grpName));

        user.setUsername("true");

        return user;
    }

    // Method for deleting a group
    @RequestMapping(value="/deleteGroup/{grpName}")
    public Date deleteGroup(@PathVariable String grpName) {
        int grpId = searchService.findGroup(grpName).getGrpId();
        scheduleService.deleteGroup(grpId);
        return new Date();
    }

}
