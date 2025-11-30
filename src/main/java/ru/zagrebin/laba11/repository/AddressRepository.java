package ru.zagrebin.laba11.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.zagrebin.laba11.entity.Address;
import ru.zagrebin.laba11.entity.Student;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AddressRepository {
    private JdbcTemplate jdbcTemplate;

    public AddressRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Address> findAll() {
        return jdbcTemplate.query("select * from address", (rs, rowNum) -> {
            Address a = new Address();
            List<Student> students = new StudentRepository(new JdbcTemplate()).findAll();
            a.setId(rs.getLong("id"));
            a.setCity(rs.getString("city"));
            a.setStreet(rs.getString("street"));
            a.setHouse(rs.getString("house"));
            a.setApartment(rs.getString("apartment"));
            a.setStudents(students);
            return a;
        });
    }
}