package nextstep.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcTemplate {
    private static final Logger logger = LoggerFactory.getLogger(JdbcTemplate.class);

    private static JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    private JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static JdbcTemplate getInstance(DataSource dataSource) {
        if (jdbcTemplate == null) {
            return new JdbcTemplate(dataSource);
        }
        return jdbcTemplate;
    }

    public void executeQuery(String query, Object... objects) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = createPreparedStatement(con, query, objects)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error occurred while executing Query", e);
            throw new JdbcTemplateException(e);
        }
    }

    private PreparedStatement createPreparedStatement(Connection con, String sql, Object... objects) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(sql);
        for (int i = 0; i < objects.length; i++) {
            pstmt.setObject(i + 1, objects[i]);
        }
        return pstmt;
    }
}
