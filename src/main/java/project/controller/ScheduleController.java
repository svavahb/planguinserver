package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.persistence.entities.*;
import project.persistence.repositories.Repository;
import project.service.ScheduleService;
import project.service.SearchService;
import project.validator.ItemValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by halld on 02-Nov-16.
 */

@RestController
public class ScheduleController {

    // Services for schedules, finding users and validation
    private ScheduleService scheduleService;
    private SearchService searchService;

    // Constructor
    @Autowired
    public ScheduleController(ScheduleService scheduleService, SearchService searchService){
        this.scheduleService = scheduleService;
        this.searchService = searchService;
    }

    // Get schedule filtered by selectedFilter
    @RequestMapping(value = "/scheduleByFilter/{filter}/{loggedInUser}")
    public Schedule viewGetScheduleByFilters(@PathVariable String filter, @PathVariable String loggedInUser){
        // Find logged in user info
        User tmpUser = searchService.findByName(loggedInUser);
        int userId = tmpUser.getUserId();

        // Get current week no and year
        int yearNow = LocalDateTime.now().getYear();
        int weekNow = scheduleService.findWeekNo(LocalDateTime.now());

        // Find scheduleItems that have selectedFilter
        List<ScheduleItem> scheduleItemList = scheduleService.scheduleItemsFilters(userId, weekNow, yearNow, filter);

        Schedule scheduleByFilters = new Schedule();
        scheduleByFilters.setUser(tmpUser);
        for ( ScheduleItem s : scheduleItemList) {
            scheduleByFilters.addItem(s);
        }

        return scheduleByFilters;
    }

    // Post method for editing items, not fully implemented
    @RequestMapping(value = "/schedule/edit/{itemId}", method = RequestMethod.POST)
    public ResponseEntity editSchedulePost(@RequestBody ScheduleItem scheduleItem, @PathVariable int itemId){

        scheduleService.editScheduleItem(itemId, scheduleItem.getTitle(),
                scheduleItem.getUserId(), scheduleItem.getStartTime(), scheduleItem.getEndTime(),
                scheduleItem.getWeekNo(), scheduleItem.getYear(), scheduleItem.getLocation(),
                scheduleItem.getColor(), scheduleItem.getDescription(), scheduleItem.getTaggedUsers(),
                scheduleItem.getFilters());

        return new ResponseEntity(HttpStatus.OK);
    }

    // Method for deleting schedule items
    @RequestMapping(value="/deleteItem")
    public ResponseEntity deleteItemPost(@RequestParam("itemId") int itemId) {
        scheduleService.removeItem(itemId);
        return new ResponseEntity(HttpStatus.OK);
    }

    // Post method for inserting an item into the logged in user's schedule
    @RequestMapping(value = "/createItem/{loggedInUser}", method = RequestMethod.POST)
    //@PostMapping(value = "/home")
    public User insertItemPost(@RequestBody ScheduleItem scheduleItem, @PathVariable String loggedInUser) {
        // Get logged in user and info
        User tmpUser= scheduleService.findUserByUsername(loggedInUser);
        int userId = tmpUser.getUserId();

        // Find week no and year of the new item
        int year = scheduleItem.getStartTime().getYear();
        Date startTime = scheduleItem.getStartTime();

        LocalDateTime start = LocalDateTime.of(startTime.getYear(),startTime.getMonth(),startTime.getDayOfMonth(),startTime.getHour(),startTime.getMinute());
        int weekNo = scheduleService.findWeekNo(start);

        System.out.println("dagur: "+scheduleItem.getStartTime().getDayOfMonth()+" mánuður: "+scheduleItem.getStartTime().getMonth());

        // Create the new item
        scheduleService.createItem(scheduleItem.getTitle(), userId, scheduleItem.getStartTime(), scheduleItem.getEndTime(),
                scheduleItem.getTaggedUsers(), weekNo, year, scheduleItem.getLocation(),
                scheduleItem.getColor(),scheduleItem.getDescription(), scheduleItem.getFilter());

        return new User();
    }

    // Get base home page
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public Schedule scheduleGet(@RequestParam String loggedInUser) {

        // Get logged in user and info
        User user = scheduleService.findUserByUsername(loggedInUser);
        int userid= user.getUserId();

        // Find the current week no and year
        int yearNow = LocalDateTime.now().getYear();
        int weekNow = scheduleService.findWeekNo(LocalDateTime.now());

        // Find scheduleItems for this user
        List<ScheduleItem> scheduleItemList = scheduleService.scheduleItems(userid, weekNow, yearNow);

        Schedule scheduleByFilters = new Schedule();
        scheduleByFilters.setUser(user);
        scheduleByFilters.setItems(scheduleItemList);

        System.out.println(scheduleItemList);

        return scheduleByFilters;
    }

}
