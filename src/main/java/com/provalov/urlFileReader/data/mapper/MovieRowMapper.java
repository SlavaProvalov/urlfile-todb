package com.provalov.urlFileReader.data.mapper;

import com.provalov.urlFileReader.entity.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Viacheslav Provalov
 */

public class MovieRowMapper implements RowMapper<Movie> {

    @Override
    public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Movie.builder()
                .id(rs.getInt("id"))
                .originalName(rs.getString("original_name"))
                .name(rs.getString("name"))
                .productionYear(rs.getInt("production_year"))
                .price(rs.getDouble("price"))
                .description(rs.getString("description"))
                .rating(rs.getDouble("rating"))
                .build();
    }
}
