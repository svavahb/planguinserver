package project.persistence.repositories;

import project.persistence.entities.*;

import java.time.*;
import java.util.List;


public interface RepositoryInterface {

    List<User> findAllUsers();

    User findUsersByName(String username);

    User findUserById(int userId);

    List<ScheduleItem> findItemsByUserWeek(int userId, int weekNo, int year);

    List<ScheduleItem> findItemsByUserWeekFilter(int userId, int weekNo, int year, String filter);

    int createUser(String username, String password, String photo, String school);

    void editUser(int userId, String username, String password, String photo, String school);

    int createItem(String title, int userId, LocalDateTime startTime, LocalDateTime endTime,
                   int weekNo, int year, String location, String color, String description);

    void deleteItem(int itemId);

    void editItem(int itemId, String title, int userId, LocalDateTime startTime, LocalDateTime endTime, int weekNo, int year,
                  String location, String color, String description, List<String> taggedUsers, List<String> filters);

    void addFilterToItem(int userId, int itemId, String filterName);

    void removeFilterFromItem(int userId, int itemId, String filterName);

    Group findGroup(int grpId);

    int createGroup(String grpName);

    void deleteGroup(int grpId);

    void editGroupName(int grpId, String grpName);

    void addGroupMember(int groupId, int userId);

    void removeGroupMember(int groupId, int userId);

    void createFriendship(int userId1, int userId2);

    void deleteFriendship(int userid1, int userid2);

    void createFilter(String filterName, int userId, int itemId);

    void deleteFilter(int filterId);

}
