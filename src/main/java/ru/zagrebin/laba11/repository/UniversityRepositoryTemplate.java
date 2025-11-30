package ru.zagrebin.laba11.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.zagrebin.laba11.entity.University;
import ru.zagrebin.laba11.mapper.UniversityRowMapper;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class UniversityRepositoryTemplate {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<University> universityRowMapper;

    public UniversityRepositoryTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.universityRowMapper = new UniversityRowMapper();
    }

    public List<University> findAll() {
        String sql = "select * from university";
        return jdbcTemplate.query(sql, universityRowMapper);
    }

    public Optional<University> findById(Long id) {
        String sql = "select * from university where id = ?";
        try {
            University university = jdbcTemplate.queryForObject(sql, universityRowMapper, id);
            return Optional.ofNullable(university);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public University save(University university) {
        if (university.getId() == null) {
            String sql = "insert into university (name) values (?)";

            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, university.getName());
                return ps;
            }, keyHolder);

            Long newId = keyHolder.getKey().longValue();
            university.setId(newId);
        } else {
            String sql = "update university set name = ? where id = ?";
            jdbcTemplate.update(sql, university.getName(), university.getId());
        }
        return university;
    }

    public void deleteById(Long id) {
        String sql = "delete from university where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<University> findByNameContainingIgnoreCase(String name) {
        String sql = "select * from university where lower(name) like lower(?)";
        return jdbcTemplate.query(sql, universityRowMapper, "%" + name + "%");
    }
}