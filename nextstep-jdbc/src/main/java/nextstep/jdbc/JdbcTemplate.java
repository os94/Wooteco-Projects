package nextstep.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            try {
                con.setAutoCommit(false);
                pstmt.executeUpdate();
                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (Exception e) {
            logger.error("Error occurred while executing Query", e);
            throw new JdbcTemplateException(e);
        }
    }

    public <T> List<T> query(String query, RowMapper<T> rowMapper, Object... objects) {
        List<T> results = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = createPreparedStatement(con, query, objects);
             ResultSet rs = pstmt.executeQuery()) {
            try {
                con.setAutoCommit(false);
                while (rs.next()) {
                    T t = rowMapper.mapRow(rs);
                    results.add(t);
                }
                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (Exception e) {
            logger.error("Error occurred while executing Query", e);
            throw new JdbcTemplateException(e);
        }
        return results;
    }

    public <T> T queryForObject(String query, RowMapper<T> rowMapper, Object... objects) {
        T result = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = createPreparedStatement(con, query, objects);
             ResultSet rs = pstmt.executeQuery()) {
            try {
                con.setAutoCommit(false);
                if (rs.next()) {
                    result = rowMapper.mapRow(rs);
                }
                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (Exception e) {
            logger.error("Error occurred while executing Query", e);
            throw new JdbcTemplateException(e);
        }
        return result;
    }

    private PreparedStatement createPreparedStatement(Connection con, String sql, Object... objects) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(sql);
        for (int i = 0; i < objects.length; i++) {
            pstmt.setObject(i + 1, objects[i]);
        }
        return pstmt;
    }
}
