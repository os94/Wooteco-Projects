package lotto.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoundDAO {
    public int findCurrentRound() {
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
}
