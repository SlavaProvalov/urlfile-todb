package com.provalov.urlFileReader.data.mapper;

import com.provalov.urlFileReader.entity.Poster;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author Viacheslav Provalov
 */

public class PosterRowMapper implements RowMapper<Poster> {
    @Override
    public Poster mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Poster.builder()
                .id(rs.getInt("id"))
                .posterLink(rs.getString("poster_link"))
                .movieId(Optional.of(rs.getInt("movie_id")).orElse(0))
                .build();
    }
}
