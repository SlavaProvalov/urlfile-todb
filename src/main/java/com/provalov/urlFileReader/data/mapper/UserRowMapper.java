package com.provalov.urlFileReader.data.mapper;

import com.provalov.urlFileReader.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Viacheslav Provalov
 */

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getInt("id"))
                .login(rs.getString("login"))
                .password(rs.getString("password"))
                .userName(rs.getString("user_name"))
                .role(rs.getString("role"))
                .build();
    }
}
