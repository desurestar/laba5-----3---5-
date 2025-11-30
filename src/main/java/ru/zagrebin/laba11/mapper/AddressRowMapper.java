package ru.zagrebin.laba11.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.zagrebin.laba11.entity.Address;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressRowMapper implements RowMapper<Address> {
    @Override
    public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
        Address address = new Address();
        address.setId(rs.getLong("id"));
        address.setCity(rs.getString("city"));
        address.setStreet(rs.getString("street"));
        address.setCity(rs.getString("city"));
        address.setHouse(rs.getString("house"));
        address.setApartment(rs.getString("apartment"));

        return address;
    }
}
