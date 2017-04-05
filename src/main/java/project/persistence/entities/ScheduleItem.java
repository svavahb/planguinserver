package project.persistence.entities;
import java.time.*;
import java.util.*;

/**
 * Created by Þórunn on 11/2/2016.
 * Class for containing all info about a schedule item
 */
public class ScheduleItem {
    private String title;
    private int id;
    private int userId;
    private Date startTime;
    private Date endTime;
    private List<String> taggedUsers = new ArrayList<>();
    private int weekNo;
    private int year;
    private String location;
    private int color;
    private String description;
    private List<String> filters = new ArrayList<>();
    private String filter;

    public String getTitle(){return title;}
    public void setTitle(String title){this.title = title;}

    public int getId(){return id;}
    public void setId(int id){this.id = id;}

    public int getUserId(){return userId;}
    public void setUserId(int userId){this.userId = userId;}

    public Date getStartTime(){return startTime;}
    public void setStartTime(Date startTime){this.startTime = startTime;}

    public Date getEndTime(){return endTime;}
    public void setEndTime(Date endTime){this.endTime = endTime;}

    public void setTaggedUsers(List<String> taggedUsers){this.taggedUsers = taggedUsers;}
    public List<String> getTaggedUsers(){return taggedUsers;}
    public void addTaggeduser(String user){taggedUsers.add(user);}
    public void removeTaggedUser(String user){taggedUsers.remove(user);}

    public int getWeekNo(){return weekNo;}
    public void setWeekNo(int weekNo){this.weekNo = weekNo;}

    public int getYear(){return year;}
    public void setYear(int year){this.year = year;}

    public String getLocation(){return location;}
    public void setLocation(String location ){this.location = location;}

    public int getColor(){return color;}
    public void setColor(int color ){this.color = color;}

    public String getDescription(){return description;}
    public void setDescription(String description ){this.description = description;}

    public String getFilter(){return filter;}
    public void setFilter(String filter ){this.filter= filter;}

    public void setFilters(List<String> filters){this.filters = filters;}
    public List<String> getFilters(){return filters;}
    public void addFilter(String filter){filters.add(filter);}
    public void removeFilter(String filter){filters.remove(filter);}


}
