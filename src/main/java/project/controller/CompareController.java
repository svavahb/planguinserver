package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.persistence.entities.Group;
import project.persistence.entities.Schedule;
import project.persistence.entities.ScheduleItem;
import project.persistence.entities.User;
import project.service.CompareService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by halld on 02-Nov-16.
 */

@RestController
public class CompareController {

    CompareService compareService;

    @Autowired
    public CompareController(CompareService compareService){
        this.compareService = compareService;
    }

    // Show comparison of schedules between logged in user and selected friend
    @RequestMapping(value="/compareFriends/{loggedInUser}/{friendId}")
    public Schedule compareSchedulePost(@PathVariable String loggedInUser, @PathVariable int friendId){

        // Get logged in user and necessary info
        User user = compareService.findUserByName(loggedInUser);

        // Find current week no and year
        int yearNow = LocalDateTime.now().getYear();
        int weekNow = compareService.findWeekNo(LocalDateTime.now());

        // Get comparison list of sheduleItems
        List<ScheduleItem> scheduleItems = compareService.compareSchedules(user.getUserId(), friendId, 48, 2016);
        Schedule schedule = new Schedule();
        schedule.setUser(user);

        for (ScheduleItem s : scheduleItems) {
            schedule.addItem(s);
        }
        return schedule;
    }

    // Get comparison of schedules in group
    @RequestMapping(value="/compareGroup/{loggedInUser}/{grpId}")
    public Schedule compareGroupSchedulePost(@PathVariable String loggedInUser, @PathVariable int grpId){

        // Get logged in user and info
        User user = compareService.findUserByName(loggedInUser);

        // FInd current week no and year
        int yearNow = LocalDateTime.now().getYear();
        int weekNow = compareService.findWeekNo(LocalDateTime.now());

        // Get comparison list of scheduleItems
        List<ScheduleItem> groupSchedule = compareService.compareScheduleGroup(grpId, weekNow, yearNow);
        Schedule schedule = new Schedule();
        schedule.setUser(user);

        for (ScheduleItem s : groupSchedule) {
            schedule.addItem(s);
        }
        return schedule;
    }


}
