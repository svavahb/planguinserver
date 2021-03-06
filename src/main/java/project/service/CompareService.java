package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.joda.LocalDateTimeParser;
import org.springframework.stereotype.Service;
import project.persistence.entities.*;
import project.persistence.repositories.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by halld on 02-Nov-16.
 */

@Service
public class CompareService {

    Repository repository;

    public CompareService(){repository = new Repository();}

    // Generates a list of all time slots occupied by a schedule item from any user in a group
    public List<ScheduleItem> compareScheduleGroup(int grpId, int weekNo, int year){
        Group group = repository.findGroup(grpId);
        List<String> members = group.getMembers();
        // Get a list of all schedule items for all users in group
        List<ScheduleItem> items = new ArrayList<>();
        for (String u:members) {
            User user = repository.findUsersByName(u);
            List<ScheduleItem> item = repository.findItemsByUserWeek(user.getUserId(),weekNo,year);
            for (ScheduleItem s:item) {
                items.add(s);
            }
        }

        return items;
    }

    // Generates a list of all time slots occupied by schedule items from either user
    public List<ScheduleItem> compareSchedules(int user1, int user2, int weekNo, int year){
        List<ScheduleItem> items1 = repository.findItemsByUserWeek(user1,weekNo, year);
        List<ScheduleItem> items2 = repository.findItemsByUserWeek(user2,weekNo, year);
        // Add all items to one list
        for (ScheduleItem s:items2) {
            items1.add(s);
        }

        return items1;
    }

    // Find a user by username
    public User findUserByName(String username) {
        User user = repository.findUsersByName(username);
        return user;
    }

    // Find a group by group name
    public int findGroupId(String grpName){
        int grpId = repository.findGroupByName(grpName);
        return grpId;
    }

    // Find week no of LDT
    public int findWeekNo(LocalDateTime LDT){
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        int weekNumber = LDT.get(woy);
        if (LDT.getDayOfWeek().getValue() == 7) weekNumber = weekNumber-1;
        return weekNumber;
    }

    // Generate list of timeslot strings
    public List<String> createTimeSlots() {
        List<String> timeSlots = new ArrayList<>();
        for (int i = 6; i<=20; i++){
            for (int k = 0; k <= 5; k++ ){
                if(i<10) {
                    timeSlots.add("0"+i+":"+k+"0");
                }
                else timeSlots.add(""+i+":"+k+"0");
            }
        }
        return timeSlots;
    }
}
