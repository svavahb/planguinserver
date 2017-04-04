package project.persistence.repositories.Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import project.persistence.entities.Date;
import project.persistence.entities.ScheduleItem;

import org.springframework.jdbc.core.RowMapper;
/**
 * Created by Svava on 03.11.16.
 * Class for mapping results from database to Schedule Item object
 */
public class ItemMapper implements RowMapper {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        ScheduleItem item = new ScheduleItem();
        item.setId((int)rs.getLong("id"));
        item.setUserId(rs.getInt("userid"));
        item.setColor(rs.getString("color"));
        item.setDescription(rs.getString("description"));
        // parse start time
        LocalDateTime start = rs.getTimestamp("startTime").toLocalDateTime();
        Date startTime = new Date(start.getYear(), start.getMonthValue(), start.getDayOfMonth(), start.getHour(), start.getMinute());
        // parse end time
        LocalDateTime end = rs.getTimestamp("endTime").toLocalDateTime();
        Date endTime = new Date(end.getYear(),end.getMonthValue(),end.getDayOfMonth(),end.getHour(),end.getMinute());

        item.setEndTime(endTime);
        item.setStartTime(startTime);

        item.setLocation(rs.getString("location"));
        item.setTitle(rs.getString("title"));
        item.setWeekNo(rs.getInt("weekNo"));
        item.setYear(rs.getInt("year"));
        return item;
    }
}
