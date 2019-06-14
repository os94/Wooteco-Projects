package lotto.model.dao;

import lotto.model.Lottos;
import lotto.model.lottogenerator.LottoFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LottosDAO {
    public Lottos findAllLottosByRound(int round) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Lottos lottos = new Lottos();

        try {
            connection = DBManager.getConnection();
            String query = "SELECT * FROM lottos_tb WHERE fk_lottos_round = ?";
            statement = connection.prepareStatement(query);

            statement.setInt(1, round);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String lotto = resultSet.getString("lotto");
                lottos.add(LottoFactory.createManualGenerator(lotto));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            DBManager.close(connection, statement, resultSet);
        }
        return lottos;
    }

    public void addLotto(String lotto, int round) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBManager.getConnection();
            String query = "INSERT INTO lottos_tb VALUES (?, ?)";
            statement = connection.prepareStatement(query);

            statement.setString(1, lotto);
            statement.setInt(2, round);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            DBManager.close(connection, statement, null);
        }
    }
}
