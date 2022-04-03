package com.provalov.urlFileReader.data.mapper;

import com.provalov.urlFileReader.entity.Country;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Viacheslav Provalov
 */

public class CountryRowMapper implements RowMapper<Country> {

    @Override
    public Country mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Country.builder()
                .id(rs.getInt("id"))
                .country(rs.getString("country"))
                .build();

    }
}
