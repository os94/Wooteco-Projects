package lotto.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
}
