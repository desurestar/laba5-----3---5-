package ru.zagrebin.laba11.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.zagrebin.laba11.entity.Course;
import ru.zagrebin.laba11.mapper.CourseRowMapper;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class CourseRepositoryTemplate {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Course> courseRowMapper;

    public CourseRepositoryTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.courseRowMapper = new CourseRowMapper();
    }

    public List<Course> findAll() {
        String sql = "select * from course";
        return jdbcTemplate.query(sql, courseRowMapper);
    }

    public Optional<Course> findById(Long id) {
        String sql = "select * from course where id = ?";
        try {
            Course course = jdbcTemplate.queryForObject(sql, courseRowMapper, id);
            return Optional.ofNullable(course);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Course> findByStudentId(Long studentId) {
        String sql = "select c.* from course c " +
                "join student_courses sc on c.id = sc.course_id " +
                "where sc.student_id = ?";
        return jdbcTemplate.query(sql, courseRowMapper, studentId);
    }

    public Course save(Course course) {
        if (course.getId() == null) {
            String sql = "insert into course (name, semester, hours) values (?, ?, ?)";

            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, course.getName());
                ps.setString(2, course.getSemester());
                ps.setString(3, course.getHours());
                return ps;
            }, keyHolder);

            Long newId = keyHolder.getKey().longValue();
            course.setId(newId);
        } else {
            String sql = "update course set name = ?, semester = ?, hours = ? where id = ?";
            jdbcTemplate.update(sql,
                    course.getName(),
                    course.getSemester(),
                    course.getHours(),
                    course.getId());
        }
        return course;
    }

    public void deleteById(Long id) {
        String deleteStudentCoursesSql = "delete from student_courses where course_id = ?";
        jdbcTemplate.update(deleteStudentCoursesSql, id);

        String sql = "delete from course where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Course> findByNameContainingIgnoreCase(String name) {
        String sql = "select * from course where lower(name) like lower(?)";
        return jdbcTemplate.query(sql, courseRowMapper, "%" + name + "%");
    }

    public List<Course> findBySemesterContainingIgnoreCase(String semester) {
        String sql = "select * from course where lower(semester) like lower(?)";
        return jdbcTemplate.query(sql, courseRowMapper, "%" + semester + "%");
    }

    public List<Course> findByHoursGreaterThanEqual(String minHours) {
        String sql = "select * from course where hours >= ?";
        return jdbcTemplate.query(sql, courseRowMapper, minHours);
    }
}