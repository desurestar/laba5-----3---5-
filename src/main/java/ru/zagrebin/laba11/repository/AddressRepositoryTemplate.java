package ru.zagrebin.laba11.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.zagrebin.laba11.entity.Address;
import ru.zagrebin.laba11.mapper.AddressRowMapper;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class AddressRepositoryTemplate {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Address> addressRowMapper;

    public AddressRepositoryTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.addressRowMapper = new AddressRowMapper();
    }

    public List<Address> findAll() {
        String sql = "select * from address";
        return jdbcTemplate.query(sql, addressRowMapper);
    }

    public Optional<Address> findById(Long id) {
        String sql = "select * from address where id = ?";
        try {
            Address address = jdbcTemplate.queryForObject(sql, addressRowMapper, id);
            return Optional.ofNullable(address);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Address save(Address address) {
        if (address.getId() == null) {
            String sql = "insert into address (city, street, house, apartment) values (?, ?, ?, ?)";

            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, address.getCity());
                ps.setString(2, address.getStreet());
                ps.setString(3, address.getHouse());
                ps.setString(4, address.getApartment());
                return ps;
            }, keyHolder);

            Long newId = keyHolder.getKey().longValue();
            address.setId(newId);
        } else {
            String sql = "update address set city = ?, street = ?, house = ?, apartment = ? where id = ?";
            jdbcTemplate.update(sql,
                    address.getCity(),
                    address.getStreet(),
                    address.getHouse(),
                    address.getApartment(),
                    address.getId());
        }
        return address;
    }

    public void deleteById(Long id) {
        String sql = "delete from address where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public List<Address> findByCityContainingIgnoreCase(String city) {
        String sql = "select * from address where lower(city) like lower(?)";
        return jdbcTemplate.query(sql, addressRowMapper, "%" + city + "%");
    }

    public List<Address> findByStreetContainingIgnoreCase(String street) {
        String sql = "select * from address where lower(street) like lower(?)";
        return jdbcTemplate.query(sql, addressRowMapper, "%" + street + "%");
    }
}