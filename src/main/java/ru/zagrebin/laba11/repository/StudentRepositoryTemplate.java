package ru.zagrebin.laba11.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.zagrebin.laba11.entity.Student;
import ru.zagrebin.laba11.mapper.StudentRowMapper;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class StudentRepositoryTemplate {
    public final JdbcTemplate jdbcTemplate;
    public final RowMapper<Student> studentRowMapper;

    public StudentRepositoryTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.studentRowMapper = new StudentRowMapper();
    }

    public List<Student> findAll() {
        String sql = "select * from student";
        return jdbcTemplate.query(sql, studentRowMapper);
    }

    public Optional<Student> findById(Long id) {
        String sql = "select * from student where id = ?";
        try {
            Student student = jdbcTemplate.queryForObject(sql, studentRowMapper, id);
            return Optional.ofNullable(student);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Student> findByAddressId(Long addressId) {
        String sql = "select * from student where address_id = ?";
        return jdbcTemplate.query(sql, studentRowMapper, addressId);
    }

    public List<Student> findByCourseId(Long courseId) {
        String sql = "select s.* from student s " +
                "join student_courses sc on s.id = sc.student_id " +
                "where sc.course_id = ?";
        return jdbcTemplate.query(sql, studentRowMapper, courseId);
    }

    public List<Student> findByGroupId(Long groupId) {
        String sql = "select * from student where group_id = ?";
        return jdbcTemplate.query(sql, studentRowMapper, groupId);
    }

    public Student save(Student student) {
        if (student.getId() == null) {
            String sql = "insert into student (last_name, first_name, patronymic, gender, nationality, " +
                    "height, weight, birth_date, phone_number, gpa, speciality, address_id, group_id) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, student.getLastName());
                ps.setString(2, student.getFirstName());
                ps.setString(3, student.getPatronymic());
                ps.setString(4, student.getGender());
                ps.setString(5, student.getNationality());
                ps.setDouble(6, student.getHeight());
                ps.setDouble(7, student.getWeight());
                ps.setDate(8, java.sql.Date.valueOf(student.getBirthDate()));
                ps.setString(9, student.getPhoneNumber());
                ps.setDouble(10, student.getGpa());
                ps.setString(11, student.getSpeciality());
                ps.setObject(12, student.getAddress() != null ? student.getAddress().getId() : null);
                ps.setObject(13, student.getStudentGroup() != null ? student.getStudentGroup().getId() : null);
                return ps;
            }, keyHolder);

            Long newId = keyHolder.getKey().longValue();
            student.setId(newId);
        } else {
            String sql = "update student set last_name = ?, first_name = ?, patronymic = ?, gender = ?, " +
                    "nationality = ?, height = ?, weight = ?, birth_date = ?, phone_number = ?, " +
                    "gpa = ?, speciality = ?, address_id = ?, group_id = ? where id = ?";

            jdbcTemplate.update(sql,
                    student.getLastName(),
                    student.getFirstName(),
                    student.getPatronymic(),
                    student.getGender(),
                    student.getNationality(),
                    student.getHeight(),
                    student.getWeight(),
                    student.getBirthDate(),
                    student.getPhoneNumber(),
                    student.getGpa(),
                    student.getSpeciality(),
                    student.getAddress() != null ? student.getAddress().getId() : null,
                    student.getStudentGroup() != null ? student.getStudentGroup().getId() : null,
                    student.getId());
        }
        return student;
    }

    public void deleteById(Long id) {
        String deleteCoursesSql = "delete from student_courses where student_id = ?";
        jdbcTemplate.update(deleteCoursesSql, id);

        String sql = "delete from student where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public boolean existsById(Long id) {
        String sql = "select count(*) from student where id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    public List<Student> findByLastNameContainingIgnoreCase(String lastName) {
        String sql = "select * from student where lower(last_name) like lower(?)";
        return jdbcTemplate.query(sql, studentRowMapper, "%" + lastName + "%");
    }

    public List<Student> findByGpaGreaterThanEqual(Double gpa) {
        String sql = "select * from student where gpa >= ?";
        return jdbcTemplate.query(sql, studentRowMapper, gpa);
    }

    public List<Student> findByCity(String city) {
        String sql = "select s.* from student s " +
                "join address a on s.address_id = a.id " +
                "where lower(a.city) like lower(?)";
        return jdbcTemplate.query(sql, studentRowMapper, "%" + city + "%");
    }

    public List<Student> findByGroupName(String groupName) {
        String sql = "select s.* from student s " +
                "join student_group sg on s.group_id = sg.id " +
                "where lower(sg.name) like lower(?)";
        return jdbcTemplate.query(sql, studentRowMapper, "%" + groupName + "%");
    }

    public List<Student> findBySpeciality(String speciality) {
        String sql = "select s.* from student s " +
                "join student_group sg on s.group_id = sg.id " +
                "where lower(sg.speciality) like lower(?)";
        return jdbcTemplate.query(sql, studentRowMapper, "%" + speciality + "%");
    }

    public List<Student> findByLastNameAndGpa(String lastName, Double gpa) {
        String sql = "select * from student where lower(last_name) like lower(?) and gpa >= ?";
        return jdbcTemplate.query(sql, studentRowMapper, "%" + lastName + "%", gpa);
    }

    public List<Student> findByCityAndSpeciality(String city, String speciality) {
        String sql = "select s.* from student s " +
                "join address a on s.address_id = a.id " +
                "join student_group sg on s.group_id = sg.id " +
                "where lower(a.city) like lower(?) and lower(sg.speciality) like lower(?)";
        return jdbcTemplate.query(sql, studentRowMapper, "%" + city + "%", "%" + speciality + "%");
    }

    public List<Student> findByLastNameAndGroupName(String lastName, String groupName) {
        String sql = "select s.* from student s " +
                "join student_group sg on s.group_id = sg.id " +
                "where lower(s.last_name) like lower(?) and lower(sg.name) like lower(?)";
        return jdbcTemplate.query(sql, studentRowMapper, "%" + lastName + "%", "%" + groupName + "%");
    }

    public List<Student> searchCombined(String lastName, String city, Double minGpa) {
        StringBuilder sql = new StringBuilder("select s.* from student s ");
        List<Object> params = new ArrayList<>();

        boolean hasAddress = city != null && !city.trim().isEmpty();
        if (hasAddress) {
            sql.append("join address a on s.address_id = a.id ");
        }

        sql.append("where 1=1 ");

        if (lastName != null && !lastName.trim().isEmpty()) {
            sql.append("and lower(s.last_name) like lower(?) ");
            params.add("%" + lastName + "%");
        }

        if (hasAddress) {
            sql.append("and lower(a.city) like lower(?) ");
            params.add("%" + city + "%");
        }

        if (minGpa != null) {
            sql.append("and s.gpa >= ? ");
            params.add(minGpa);
        }

        return jdbcTemplate.query(sql.toString(), studentRowMapper, params.toArray());
    }
}