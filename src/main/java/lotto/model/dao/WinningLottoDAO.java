package lotto.model.dao;

import lotto.model.dto.WinningLottoDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WinningLottoDAO {
    public void addWinningLotto(WinningLottoDTO winningLottoDTO) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBManager.getConnection();
            String query = "INSERT INTO winninglotto_tb VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);

            statement.setInt(1, winningLottoDTO.getFirst());
            statement.setInt(2, winningLottoDTO.getSecond());
            statement.setInt(3, winningLottoDTO.getThird());
            statement.setInt(4, winningLottoDTO.getFourth());
            statement.setInt(5, winningLottoDTO.getFifth());
            statement.setInt(6, winningLottoDTO.getSixth());
            statement.setInt(7, winningLottoDTO.getBonus());
            statement.setInt(8, winningLottoDTO.getFk_lotto_round());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            DBManager.close(connection, statement, null);
        }
    }
}
