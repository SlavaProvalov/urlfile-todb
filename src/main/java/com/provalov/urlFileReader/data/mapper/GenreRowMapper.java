package com.provalov.urlFileReader.data.mapper;

import com.provalov.urlFileReader.entity.Genre;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Viacheslav Provalov
 */

public class GenreRowMapper implements RowMapper<Genre> {

    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Genre.builder()
                .id(rs.getInt("id"))
                .genreName(rs.getString("genre_name"))
                .build();

    }
}
