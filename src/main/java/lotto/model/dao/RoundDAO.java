package lotto.model.dao;

import lotto.model.Money;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoundDAO {
    public void updateRoundWith(String inputMoney) {
        Connection connection;
        PreparedStatement statement = null;

        try {
            connection = DBManager.getConnection();
            String query = "INSERT INTO round VALUES (0, ?)";
            statement = connection.prepareStatement(query);

            statement.setInt(1, Integer.parseInt(inputMoney));
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            DBManager.close(statement, null);
        }
    }

    public int recentRound() {
        Connection connection;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBManager.getConnection();
            String query = "select count(*) from round";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                throw new SQLException();
            }
            return resultSet.getInt("count(*)");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            DBManager.close(statement, resultSet);
        }
        return -1;
    }

    public Money findMoneyByRound(int round) {
        Connection connection;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBManager.getConnection();
            String query = "SELECT * FROM round WHERE round = ?";
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
            DBManager.close(statement, resultSet);
        }
        return null;
    }
}
