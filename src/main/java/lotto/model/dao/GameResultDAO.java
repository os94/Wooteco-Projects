package lotto.model.dao;

import lotto.model.dto.GameResultDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GameResultDAO {
    public void addGameResult(GameResultDTO gameResultDTO) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBManager.getConnection();
            String query = "INSERT INTO gameresult_tb VALUES (?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);

            statement.setInt(1, gameResultDTO.getFirst());
            statement.setInt(2, gameResultDTO.getSecond());
            statement.setInt(3, gameResultDTO.getThird());
            statement.setInt(4, gameResultDTO.getFourth());
            statement.setInt(5, gameResultDTO.getFifth());
            statement.setInt(6, gameResultDTO.getFk_lotto_round());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            DBManager.close(connection, statement, null);
        }
    }
}
