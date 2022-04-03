package com.provalov.urlFileReader.data.mapper;

import com.provalov.urlFileReader.entity.Review;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author Viacheslav Provalov
 */

public class ReviewRowMapper implements RowMapper<Review> {
    @Override
    public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Review.builder()
                .id(rs.getInt("id"))
                .movieId(Optional.of(rs.getInt("movie_id")).orElse(0))
                .rating(Optional.of(rs.getDouble("rating")).orElse(0.0))
                .userId(Optional.of(rs.getInt("user_id")).orElse(0))
                .review(rs.getString("review"))
                .build();
    }
}
