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
import java.util.Optional;

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

    public void executeQuery(String query, PreparedStatementSetter setter) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = createPreparedStatement(con, query, setter)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error occurred while executing Query", e);
            throw new JdbcTemplateException(e);
        }
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

    public <T> List<T> query(String query, RowMapper<T> rowMapper, PreparedStatementSetter setter) {
        List<T> results = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = createPreparedStatement(con, query, setter);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                T t = rowMapper.mapRow(rs);
                results.add(t);
            }
        } catch (Exception e) {
            logger.error("Error occurred while executing Query", e);
            throw new JdbcTemplateException(e);
        }
        return results;
    }

    public <T> List<T> query(String query, RowMapper<T> rowMapper, Object... objects) {
        List<T> results = new ArrayList<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = createPreparedStatement(con, query, objects);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                T t = rowMapper.mapRow(rs);
                results.add(t);
            }
        } catch (Exception e) {
            logger.error("Error occurred while executing Query", e);
            throw new JdbcTemplateException(e);
        }
        return results;
    }

    public <T> Optional<T> queryForObject(String query, RowMapper<T> rowMapper, PreparedStatementSetter setter) {
        Optional<T> result = Optional.empty();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = createPreparedStatement(con, query, setter);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                result = Optional.of(rowMapper.mapRow(rs));
            }
        } catch (Exception e) {
            logger.error("Error occurred while executing Query", e);
            throw new JdbcTemplateException(e);
        }
        return result;
    }

    public <T> Optional<T> queryForObject(String query, RowMapper<T> rowMapper, Object... objects) {
        Optional<T> result = Optional.empty();
        try (Connection con = dataSource.getConnection();
             PreparedStatement pstmt = createPreparedStatement(con, query, objects);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                result = Optional.of(rowMapper.mapRow(rs));
            }
        } catch (Exception e) {
            logger.error("Error occurred while executing Query", e);
            throw new JdbcTemplateException(e);
        }
        return result;
    }

    private PreparedStatement createPreparedStatement(Connection con, String query, PreparedStatementSetter setter) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(query);
        setter.setValues(pstmt);
        return pstmt;
    }

    private PreparedStatement createPreparedStatement(Connection con, String query, Object... objects) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(query);
        for (int i = 0; i < objects.length; i++) {
            pstmt.setObject(i + 1, objects[i]);
        }
        return pstmt;
    }
}
