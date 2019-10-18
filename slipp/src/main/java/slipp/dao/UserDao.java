package slipp.dao;

import nextstep.jdbc.JdbcTemplate;
import slipp.domain.User;
import slipp.support.db.ConnectionManager;

import java.util.List;
import java.util.Optional;

public class UserDao {
    private JdbcTemplate jdbcTemplate;

    private UserDao() {
        this.jdbcTemplate = JdbcTemplate.getInstance(ConnectionManager.getDataSource());
    }

    private static class LazyHolder {
        private static final UserDao INSTANCE = new UserDao();
    }

    public static UserDao getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void insert(User user) {
        jdbcTemplate.executeQuery(
                "INSERT INTO USERS (userId, password, name, email) VALUES (?, ?, ?, ?)",
                pstmt -> {
                    pstmt.setObject(1, user.getUserId());
                    pstmt.setObject(2, user.getPassword());
                    pstmt.setObject(3, user.getName());
                    pstmt.setObject(4, user.getEmail());
                });
    }

    public void update(User user) {
        jdbcTemplate.executeQuery(
                "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?",
                pstmt -> {
                    pstmt.setObject(1, user.getPassword());
                    pstmt.setObject(2, user.getName());
                    pstmt.setObject(3, user.getEmail());
                    pstmt.setObject(4, user.getUserId());
                });
    }

    public List<User> findAll() {
        return jdbcTemplate.query(
                "SELECT userId, password, name, email FROM USERS",
                rs -> new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")),
                pstmt -> {
                });
    }

    public Optional<User> findByUserId(String userId) {
        return jdbcTemplate.queryForObject(
                "SELECT userId, password, name, email FROM USERS WHERE userId=?",
                rs -> new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")),
                pstmt -> pstmt.setObject(1, userId));
    }
}
