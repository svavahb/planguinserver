package project.service;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import project.persistence.entities.Date;
import project.persistence.entities.ScheduleItem;
import project.persistence.entities.User;
import project.persistence.repositories.Repository;

import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by halld on 02-Nov-16.
 */

@Service
public class ScheduleService {

    Repository  repository;
    SearchService searchService;


    public ScheduleService(){

        this.repository = new Repository();
        this.searchService = new SearchService();
    }

    // Get schedule items by useris, week no and year
    public List<ScheduleItem> scheduleItems(int userId, int month, int year){
        return repository.findItemsByUserWeek(userId, month, year);
    }

    // Get items by userid, week no, year and filter
    public List<ScheduleItem> scheduleItemsFilters(int userId, int weekNo, int yearNo, String filter){
        return repository.findItemsByUserWeekFilter(userId, weekNo, yearNo, filter);
    }

    // Edit schedule item (never used in this version)
    public ScheduleItem editScheduleItem(int itemId, String title, int userId, Date startTime,
                                                Date endTime, int weekNo, int year, String location, int color,
                                         String description, List<String> taggedUsers, List<String> filters){

        // Create LocalDateTime variales to insert into database correctly
        LocalDateTime start = LocalDateTime.of(startTime.getYear(), startTime.getMonth(), startTime.getDayOfMonth(), startTime.getHour(), startTime.getMinute());
        LocalDateTime end = LocalDateTime.of(endTime.getYear(), endTime.getMonth(), endTime.getDayOfMonth(), endTime.getHour(), endTime.getMinute());

        repository.editItem(itemId, title, userId, start, end, weekNo, year, location, color, description,
                taggedUsers, filters);
        ScheduleItem itemEdit = new ScheduleItem();
        itemEdit.setId(itemId);
        itemEdit.setTitle(title);
        itemEdit.setUserId(userId);
        itemEdit.setStartTime(startTime);
        itemEdit.setEndTime(endTime);
        itemEdit.setWeekNo(weekNo);
        itemEdit.setYear(year);
        itemEdit.setLocation(location);
        itemEdit.setColor(color);
        itemEdit.setDescription(description);
        itemEdit.setTaggedUsers(taggedUsers);
        itemEdit.setFilters(filters);

        return itemEdit;
    }

    // Delete item by id
    public void removeItem(int itemId){

        repository.deleteItem(itemId);
    }

    // Creates a schedule item
    public void createItem(String title, int userId, Date startTime, Date endTime,
                                   List<String> taggedUsers, int weekNo, int year, String location, int color,
                                   String description, List<String> filters){

        // Create LocalDateTime variales to insert into database correctly
        LocalDateTime start = LocalDateTime.of(startTime.getYear(), startTime.getMonth(), startTime.getDayOfMonth(), startTime.getHour(), startTime.getMinute());
        LocalDateTime end = LocalDateTime.of(endTime.getYear(), endTime.getMonth(), endTime.getDayOfMonth(), endTime.getHour(), endTime.getMinute());

        repository.createItem(title, userId, start, end, weekNo, year, location, color, description, filters);
    }

    // Creates a schedule item
    public void createMultipleItem(String title, int userId, Date startTime, Date endTime,
                           List<String> taggedUsers, int weekNo, int year, String location, int color,
                           String description, List<String> filters){

        // Create LocalDateTime variales to insert into database correctly
        LocalDateTime start = LocalDateTime.of(startTime.getYear(), startTime.getMonth(), startTime.getDayOfMonth(), startTime.getHour(), startTime.getMinute());
        LocalDateTime end = LocalDateTime.of(endTime.getYear(), endTime.getMonth(), endTime.getDayOfMonth(), endTime.getHour(), endTime.getMinute());

        repository.createItem(title, userId, start, end, weekNo, year, location, color, description, filters);
        LocalDateTime start1 = start.plusDays(7);
        LocalDateTime end1 = end.plusDays(7);
        repository.createItem(title, userId, start1, end1, weekNo, year, location, color, description, filters);
        LocalDateTime start2 = start1.plusDays(7);
        LocalDateTime end2 = end1.plusDays(7);
        repository.createItem(title, userId, start2, end2, weekNo, year, location, color, description, filters);
        LocalDateTime start3 = start2.plusDays(7);
        LocalDateTime end3 = end2.plusDays(7);
        repository.createItem(title, userId, start3, end3, weekNo, year, location, color, description, filters);

    }

    // Find a user by username
    public User findUserByUsername(String username){
        User finduser = repository.findUsersByName(username);
        return finduser;
    }

    // Parse string into right format date string
    public String changeStringDateToRigthDate(String date){
        String newDate = date.substring(6,10) +"-"+date.substring(0,2) +"-"+ date.substring(3,5);
        return newDate;
    }

    // Find year of date string
    public int findYear(String date){
        String stYear = date.substring(6,10);
        int year = Integer.parseInt(stYear);
        return year;
    }

    // Find week no of LDT
    public int findWeekNo(LocalDateTime LDT){
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
        int weekNumber = LDT.get(woy);
        if (LDT.getDayOfWeek().getValue() == 7) weekNumber = weekNumber-1;
        return weekNumber;
    }

    // Chnage format of time string (add seconds)
    public String changeformatOfTime(String time) {
        String newTime = time + ":00";
        return newTime;
    }

    // Create a group. Returns true if successful, false otherwise
    public boolean createGroup(String name, int userId) {
        // If a group is found by same name, return false
        if (repository.findGroupByName(name)!=-1) return false;
        repository.createGroup(name, userId);
        return true;
    }

    // Delete a group
    public void deleteGroup(int grpId) {
        repository.deleteGroup(grpId);
    }

    // Generate a list of timeslot strings
    public List <String> getTimeSlots(){
        List <String> TimeSlots = new ArrayList<String>();
        for (int i = 6; i<=20; i++){
            for (int k = 0; k <= 5; k++ ){
                if(i<10) {
                    TimeSlots.add("0"+i+":"+k+"0");
                }
                else TimeSlots.add(""+i+":"+k+"0");
            }
        }
        return TimeSlots;
    }

    // Generates a list of allowed filters. Will hopefully not be needed in next version (allow users to create own filters)
    public List<String> createfilterList(){
        List <String> Filters = new ArrayList<String>();
        Filters.add("Work");
        Filters.add("School");
        Filters.add("Appointment");
        Filters.add("Other");

        return Filters;
    }

    // Check if end time is before start time
    public boolean checkTime(String starts, String ends){
        int hss =Integer.parseInt(checkifZero(starts.substring(0,2)));
        int hes =Integer.parseInt(checkifZero(ends.substring(0,2)));
        int mss =Integer.parseInt(starts.substring(3,5));
        int mes =Integer.parseInt(ends.substring(3,5));
        boolean retval = true;
        if (hes < hss)retval = false;
        if (hes == hss) {
            if (mes < mss) retval = false;
        }
        return retval;
    }

    // Check if time string starts with 0 or not (9:00 vs 09:00) and add a zero if not
    public String checkifZero(String check) {
        if (check.substring(0,1) == "0") return check.substring(1);
        else return check;
    }

    public List<String> getFilters(int userid) {
        return repository.getFilters(userid);
    }


    // Delete a friendship between user1 and user2
    public void deleteFriendship(int userid1, int userid2) {
        repository.deleteFriendship(userid1, userid2);
    }

}
