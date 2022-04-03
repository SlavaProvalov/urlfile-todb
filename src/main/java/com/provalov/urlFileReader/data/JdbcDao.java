package com.provalov.urlFileReader.data;

import ch.qos.logback.core.util.StringCollectionUtil;
import com.provalov.urlFileReader.data.mapper.*;
import com.provalov.urlFileReader.entity.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @author Viacheslav Provalov
 */

@Repository
public class JdbcDao {
    private static final Map<Class<?>, RowMapper<?>> ROW_MAPPER_CLASS_MAP = new HashMap<>();

    static {
        ROW_MAPPER_CLASS_MAP.put(Country.class, new CountryRowMapper());
        ROW_MAPPER_CLASS_MAP.put(Genre.class, new GenreRowMapper());
        ROW_MAPPER_CLASS_MAP.put(Movie.class, new MovieRowMapper());
        ROW_MAPPER_CLASS_MAP.put(Poster.class, new PosterRowMapper());
        ROW_MAPPER_CLASS_MAP.put(Review.class, new ReviewRowMapper());
        ROW_MAPPER_CLASS_MAP.put(User.class, new UserRowMapper());
    }

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public boolean insert(String tableName, List<String> colNames, List<List<String>> rows) {
        StringBuilder query = new StringBuilder("INSERT INTO " + tableName + " ");
        StringJoiner columnNames = new StringJoiner(", ", "(", ")");
        colNames.forEach(columnNames::add);
        query.append(columnNames)
                .append(" VALUES ");
        StringJoiner sjRowValues = new StringJoiner(", ");
        for (List<String> row : rows) {
            StringJoiner sjRow = new StringJoiner(",", "(", ")");
            for (String value : row) {
                if (NumberUtils.isCreatable(value)) {
                    sjRow.add(value);
                } else {
                    sjRow.add("'" + value.replaceAll("'", "''") + "'");
                }
            }
            sjRowValues.add(sjRow.toString());
        }
        query.append(sjRowValues);

        return jdbcTemplate.update(query.toString()) > 0;

    }

    public <T> List<T> getAll(Class<T> clazz, String tableName, List<String> valueNames) {
        valueNames.add("id"); // BE CAREFUL!!! takes  additional 'id' column for all queries
        StringBuilder query = new StringBuilder("SELECT ");
        StringJoiner sj = new StringJoiner(", ");
        valueNames.forEach(sj::add);
        query.append(sj).append(" FROM ").append(tableName);

        return (List<T>) jdbcTemplate.query(query.toString(), ROW_MAPPER_CLASS_MAP.get(clazz));
    }

}
