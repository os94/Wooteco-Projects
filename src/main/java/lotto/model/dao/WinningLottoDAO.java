package lotto.model.dao;

import lotto.model.LottoNumber;
import lotto.model.WinningLotto;
import lotto.model.lottogenerator.LottoFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WinningLottoDAO {
    public WinningLotto findWinningLottoByRound(int round) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBManager.getConnection();
            String query = "SELECT * FROM winninglotto_tb WHERE fk_winninglotto_round = ?";
            statement = connection.prepareStatement(query);

            statement.setInt(1, round);
            resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                throw new SQLException();
            }
            return new WinningLotto(
                    LottoFactory.createManualGenerator(resultSet.getString("winninglotto")),
                    LottoNumber.of(resultSet.getInt("bonus")));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            DBManager.close(connection, statement, resultSet);
        }
        return null;
    }

    public void addWinningLotto(String winningLotto, int bonus, int round) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBManager.getConnection();
            String query = "INSERT INTO winninglotto_tb VALUES (?, ?, ?)";
            statement = connection.prepareStatement(query);

            statement.setString(1, winningLotto);
            statement.setInt(2, bonus);
            statement.setInt(3, round);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            DBManager.close(connection, statement, null);
        }
    }
}
