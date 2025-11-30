package ru.zagrebin.laba11.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.zagrebin.laba11.entity.StudentGroup;
import ru.zagrebin.laba11.entity.University;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentGroupRowMapper implements RowMapper<StudentGroup> {
    @Override
    public StudentGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
        StudentGroup studentGroup = new StudentGroup();
        studentGroup.setId(rs.getLong("id"));
        studentGroup.setName(rs.getString("name"));
        studentGroup.setSpeciality(rs.getString("speciality"));

        Long universityId = rs.getLong("university_id");
        if (!rs.wasNull() && universityId != 0) {
            University university = new University();
            university.setId(universityId);
            studentGroup.setUniversity(university);
        }

        return studentGroup;

    }
}
