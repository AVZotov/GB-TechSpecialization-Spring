package ru.gb.repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.gb.entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name")
        );
    }
}
