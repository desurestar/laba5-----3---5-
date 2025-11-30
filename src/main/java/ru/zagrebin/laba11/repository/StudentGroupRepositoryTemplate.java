package ru.zagrebin.laba11.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.zagrebin.laba11.entity.StudentGroup;
import ru.zagrebin.laba11.mapper.StudentGroupRowMapper;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentGroupRepositoryTemplate {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<StudentGroup> studentGroupRowMapper;

    public StudentGroupRepositoryTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.studentGroupRowMapper = new StudentGroupRowMapper();
    }

    public List<StudentGroup> findAll() {
        String sql = "select * from student_group";
        return jdbcTemplate.query(sql, studentGroupRowMapper);
    }

    public Optional<StudentGroup> findById(Long id) {
        String sql = "select * from student_group where id = ?";
        try {
            StudentGroup studentGroup = jdbcTemplate.queryForObject(sql, studentGroupRowMapper, id);
            return Optional.ofNullable(studentGroup);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<StudentGroup> findByUniversityId(Long universityId) {
        String sql = "select * from student_group where university_id = ?";
        return jdbcTemplate.query(sql, studentGroupRowMapper, universityId);
    }

    public StudentGroup save(StudentGroup studentGroup) {
        if (studentGroup.getId() == null) {
            String sql = "insert into student_group (name, speciality, university_id) values (?, ?, ?)";

            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, studentGroup.getName());
                ps.setString(2, studentGroup.getSpeciality());
                ps.setObject(3, studentGroup.getUniversity() != null ? studentGroup.getUniversity().getId() : null);
                return ps;
            }, keyHolder);

            Long newId = keyHolder.getKey().longValue();
            studentGroup.setId(newId);
        } else {
            String sql = "update student_group set name = ?, speciality = ?, university_id = ? where id = ?";
            jdbcTemplate.update(sql,
                    studentGroup.getName(),
                    studentGroup.getSpeciality(),
                    studentGroup.getUniversity() != null ? studentGroup.getUniversity().getId() : null,
                    studentGroup.getId());
        }
        return studentGroup;
    }

    public void deleteById(Long id) {
        String sql = "delete from student_group where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<StudentGroup> findByNameContainingIgnoreCase(String name) {
        String sql = "select * from student_group where lower(name) like lower(?)";
        return jdbcTemplate.query(sql, studentGroupRowMapper, "%" + name + "%");
    }

    public List<StudentGroup> findBySpecialityContainingIgnoreCase(String speciality) {
        String sql = "select * from student_group where lower(speciality) like lower(?)";
        return jdbcTemplate.query(sql, studentGroupRowMapper, "%" + speciality + "%");
    }

    public List<StudentGroup> findByUniversityName(String universityName) {
        String sql = "select sg.* from student_group sg " +
                "join university u on sg.university_id = u.id " +
                "where lower(u.name) like lower(?)";
        return jdbcTemplate.query(sql, studentGroupRowMapper, "%" + universityName + "%");
    }
}