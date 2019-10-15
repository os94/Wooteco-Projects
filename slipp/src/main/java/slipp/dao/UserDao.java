package slipp.dao;

import nextstep.jdbc.JdbcTemplate;
import slipp.domain.User;
import slipp.support.db.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao {
    private JdbcTemplate jdbcTemplate;

    public UserDao() {
        this.jdbcTemplate = JdbcTemplate.getInstance(ConnectionManager.getDataSource());
    }

    public void insert(User user) {
        jdbcTemplate.executeQuery(
                "INSERT INTO USERS VALUES (?, ?, ?, ?)",
                user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    private PreparedStatement createPreparedStatement(Connection con, String sql, Object... objects) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(sql);
        for (int i = 0; i < objects.length; i++) {
            pstmt.setObject(i + 1, objects[i]);
        }
        return pstmt;
    }

    public void update(User user) {
        jdbcTemplate.executeQuery(
                "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?",
                user.getPassword(), user.getName(), user.getEmail(), user.getUserId()
        );
    }

    public List<User> findAll() {
        return jdbcTemplate.query("SELECT userId, password, name, email FROM USERS", User.class);
    }

    public User findByUserId(String userId) throws SQLException {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = createPreparedStatement(con, "SELECT userId, password, name, email FROM USERS WHERE userid=?", userId);
             ResultSet rs = pstmt.executeQuery()) {

            User user = null;
            if (rs.next()) {
                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }

            return user;
        }
    }
}
