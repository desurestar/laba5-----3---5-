package ru.zagrebin.laba11.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.zagrebin.laba11.entity.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRowMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();
        student.setId(rs.getLong("id"));
        student.setLastName(rs.getString("last_name"));
        student.setFirstName(rs.getString("first_name"));
        student.setPatronymic(rs.getString("patronymic"));
        student.setGender(rs.getString("gender"));
        student.setNationality(rs.getString("nationality"));
        student.setHeight(rs.getDouble("height"));
        student.setWeight(rs.getDouble("weight"));

        java.sql.Date birthDate = rs.getDate("birth_date");
        if (birthDate != null) {
            student.setBirthDate(birthDate.toLocalDate());
        }

        student.setPhoneNumber(rs.getString("phone_number"));
        student.setGpa(rs.getDouble("gpa"));
        student.setSpeciality(rs.getString("speciality"));

        return student;
    }
}
