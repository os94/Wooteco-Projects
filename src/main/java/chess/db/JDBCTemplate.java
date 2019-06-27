package chess.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCTemplate {
    private static JDBCTemplate jdbcTemplate = null;

    private JDBCTemplate() {
    }

    public static JDBCTemplate getInstance() {
        if (jdbcTemplate == null) {
            jdbcTemplate = new JDBCTemplate();
        }
        return jdbcTemplate;
    }

    public void updateQuery(String query, Object... args) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement pstmt = createPreparedStatement(connection, query, args)) {
            pstmt.executeUpdate();
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
    }

    public void updateBatchQuery(String query, List<List<Object>> args) {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement pstmt = createBatchedPreparedStatement(connection, query, args)) {
            pstmt.executeBatch();
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
        }
    }

    public List<Map<String, Object>> selectQuery(String query, Object... args) throws SQLException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement pstmt = createPreparedStatement(connection, query, args);
             ResultSet resultSet = pstmt.executeQuery()) {
            return makeResult(resultSet);
        } catch (SQLException exception) {
            System.err.println(exception.getMessage());
            throw exception;
        }
    }

    private List<Map<String, Object>> makeResult(ResultSet resultSet) throws SQLException {
        List<Map<String, Object>> results = new ArrayList<>();

        while (resultSet.next()) {
            Map<String, Object> resultRow = makeResultRow(resultSet);
            results.add(resultRow);
        }
        return results;
    }

    private Map<String, Object> makeResultRow(ResultSet resultSet) throws SQLException {
        Map<String, Object> resultRow = new HashMap<>();
        ResultSetMetaData rsMetaData = resultSet.getMetaData();

        for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
            String columnName = rsMetaData.getColumnName(i);
            resultRow.put(columnName, resultSet.getObject(columnName));
        }
        return resultRow;
    }

    private PreparedStatement createPreparedStatement(Connection connection, String query, Object... args) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(query);

        for (int i = 0; i < args.length; i++) {
            pstmt.setObject(i + 1, args[i]);
        }
        return pstmt;
    }

    private PreparedStatement createBatchedPreparedStatement(Connection connection, String query, List<List<Object>> args) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(query);

        for (List<Object> arg : args) {
            for (int i = 0; i < arg.size(); i++) {
                pstmt.setObject(i + 1, arg.get(i));
            }
            pstmt.addBatch();
        }
        return pstmt;
    }
}
