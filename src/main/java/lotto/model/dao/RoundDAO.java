package lotto.model.dao;

import lotto.model.Money;

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

    public Money findMoneyByRound(int round) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBManager.getConnection();
            String query = "SELECT * FROM round_tb WHERE round = ?";
            statement = connection.prepareStatement(query);

            statement.setInt(1, round);
            resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                throw new SQLException();
            }
            return new Money(resultSet.getInt("money"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            DBManager.close(connection, statement, resultSet);
        }
        return null;
    }
}
