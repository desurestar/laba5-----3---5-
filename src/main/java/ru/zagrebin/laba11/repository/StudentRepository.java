package ru.zagrebin.laba11.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.zagrebin.laba11.entity.Address;
import ru.zagrebin.laba11.entity.Student;
import ru.zagrebin.laba11.mapper.StudentRowMapper;


@Repository
public class StudentRepository {
    private final JdbcTemplate jdbcTemplate;
    public StudentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Student> findAll() {
        return jdbcTemplate.query("select * form student", (rs, rowNum) -> {
            Student s = new Student();
            s.setId(rs.getLong("id"));
            s.setLastName(rs.getString("last_name"));
            s.setFirstName(rs.getString("first_name"));
            s.setPatronymic(rs.getString("patronymic"));
            s.setNationality(rs.getString("nationality"));
            s.setHeight(rs.getDouble("height"));
            s.setWeight(rs.getDouble("weight"));
            s.setBirthDate(rs.getDate("birth_date").toLocalDate());
            s.setPhoneNumber(rs.getString("phone_number"));
            s.setGpa(rs.getDouble("gpa"));
            s.setSpeciality(rs.getString("speciality"));
            s.setAddress();
            return s;
        });
    }

    public Student findById(Long id) {
        return jdbcTemplate.queryForObject("select * from student where id = ?", new Object[]{id}, new StudentRowMapper());
    }

    public void save(Student student) {
        jdbcTemplate.update("insert into student values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                student.getId(),
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
                student.getAddress(),
                student.getStudentGroup(),
                student.getCourses());
    }
}
