package slipp.dao;

import nextstep.jdbc.JdbcTemplate;
import slipp.domain.User;
import slipp.support.db.ConnectionManager;

import java.util.List;

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
                user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public void update(User user) {
        jdbcTemplate.executeQuery(
                "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?",
                user.getPassword(), user.getName(), user.getEmail(), user.getUserId()
        );
    }

//    public List<User> findAll() {
//        return jdbcTemplate.query("SELECT userId, password, name, email FROM USERS", User.class);
//    }

    public List<User> findAll() {
        return jdbcTemplate.query(
                "SELECT userId, password, name, email FROM USERS",
                rs -> new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")));
    }

    public User findByUserId(String userId) {
        return jdbcTemplate.queryForObject(
                "SELECT userId, password, name, email FROM USERS WHERE userid=?",
                User.class,
                userId);
    }

    /*public User findByUserId(String userId) {
        return jdbcTemplate.queryForObject(
                "SELECT userId, password, name, email FROM USERS WHERE userid=?",
                    rs -> new User(
                                rs.getString("userId"),
                                rs.getString("password"),
                                rs.getString("name"),
                                rs.getString("email")),
                userId);
    }*/
}
