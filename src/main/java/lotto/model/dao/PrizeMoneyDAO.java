package lotto.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrizeMoneyDAO {
    public void addPrizeMoney(int prizeMoney, double profitRate) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBManager.getConnection();
            String query = "INSERT INTO prizemoney_tb VALUES (?, ?, ?)";
            statement = connection.prepareStatement(query);

            statement.setInt(1, prizeMoney);
            statement.setDouble(2, profitRate);
            statement.setInt(3, new RoundDAO().getRound() - 1);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            DBManager.close(connection, statement, null);
        }
    }

    public int findPrizeMoneyByRound(int round) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBManager.getConnection();
            String query = "SELECT * FROM prizemoney_tb WHERE fk_prizemoney_round = ?";
            statement = connection.prepareStatement(query);

            statement.setInt(1, round);
            resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                throw new SQLException();
            }
            return resultSet.getInt("prize_money");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            DBManager.close(connection, statement, resultSet);
        }
        return -1;
    }

    public double findProfitRateByRound(int round) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBManager.getConnection();
            String query = "SELECT * FROM prizemoney_tb WHERE fk_prizemoney_round = ?";
            statement = connection.prepareStatement(query);

            statement.setInt(1, round);
            resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                throw new SQLException();
            }
            return resultSet.getDouble("profit_rate");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            DBManager.close(connection, statement, resultSet);
        }
        return -1;
    }
}
