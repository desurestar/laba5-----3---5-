package ru.zagrebin.laba11.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.zagrebin.laba11.entity.Course;
import ru.zagrebin.laba11.entity.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseRowMapper implements RowMapper<Course> {
    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        Course course = new Course();
        course.setId(rs.getLong("id"));
        course.setName(rs.getString("name"));
        course.setSemester(rs.getString("semester"));
        course.setHours(rs.getString("hours"));

        return course;
    }
}
