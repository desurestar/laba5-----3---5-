package ru.zagrebin.laba11.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.zagrebin.laba11.entity.Address;
import ru.zagrebin.laba11.entity.Student;
import ru.zagrebin.laba11.entity.StudentGroup;

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

        // Set address reference with ID for later population
        Long addressId = rs.getLong("address_id");
        if (!rs.wasNull() && addressId != 0) {
            Address address = new Address();
            address.setId(addressId);
            student.setAddress(address);
        }

        // Set student group reference with ID for later population
        Long groupId = rs.getLong("group_id");
        if (!rs.wasNull() && groupId != 0) {
            StudentGroup studentGroup = new StudentGroup();
            studentGroup.setId(groupId);
            student.setStudentGroup(studentGroup);
        }

        return student;
    }
}
