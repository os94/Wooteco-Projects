package lotto.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoundDAO {
    public int getRound() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBManager.getConnection();
            String query = "SELECT max(round) FROM round_tb";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                throw new SQLException();
            }
            return resultSet.getInt("max(round)") + 1;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            DBManager.close(connection, statement, resultSet);
        }
        return -1;
    }

    public void increaseRound() {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBManager.getConnection();
            String query = "INSERT INTO round_tb VALUES (0)";
            statement = connection.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            DBManager.close(connection, statement, null);
        }
    }
}
