package ru.gb.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.gb.entity.User;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class UserJDBCDataAccessService implements UserDao {

    private final UserRowMapper userRowMapper;
    private final JdbcTemplate jdbcTemplate;

    public UserJDBCDataAccessService(UserRowMapper userRowMapper, JdbcTemplate jdbcTemplate) {
        this.userRowMapper = userRowMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> selectAllUsers() {
        var sql = """
                SELECT id, first_name, last_name FROM users
                """;
        return jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public Optional<User> selectUserById(Integer id) {
        var sql = """
                SELECT id, first_name, last_name FROM users WHERE id = ?
                """;
        return jdbcTemplate.query(sql, userRowMapper, id)
                .stream().findFirst();
    }

    @Override
    public void insertUser(User user) {
        var sql = """
                INSERT INTO users (first_name, last_name) VALUES (?, ?)
                """;
        jdbcTemplate.update(sql, user.getFirstName(), user.getLastName());
    }

    @Override
    public void deleteUserById(Integer id) {
        var sql = """
                    DELETE FROM users WHERE id = ?
                """;
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void updateUser(User user) {
        String sql = """
                UPDATE users SET first_name = ?, last_name = ? WHERE id = ?
                """;
        jdbcTemplate.update(
                sql,
                user.getFirstName(),
                user.getLastName(),
                user.getId());
    }
}
