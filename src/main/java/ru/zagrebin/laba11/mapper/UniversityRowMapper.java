package ru.zagrebin.laba11.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.zagrebin.laba11.entity.University;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UniversityRowMapper implements RowMapper<University> {
    @Override
    public University mapRow(ResultSet rs, int rowNum) throws SQLException {
        University university = new University();
        university.setId(rs.getLong("id"));
        university.setName(rs.getString("name"));

        return  university;
    }
}
