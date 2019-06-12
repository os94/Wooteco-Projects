package lotto.model.dao;

import lotto.model.dto.LottoDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LottoDAO {
    public void addLotto(LottoDTO lottoDTO) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBManager.getConnection();
            String query = "INSERT INTO lotto_tb VALUES (?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);

            statement.setInt(1, lottoDTO.getFirst());
            statement.setInt(2, lottoDTO.getSecond());
            statement.setInt(3, lottoDTO.getThird());
            statement.setInt(4, lottoDTO.getFourth());
            statement.setInt(5, lottoDTO.getFifth());
            statement.setInt(6, lottoDTO.getSixth());
            statement.setInt(7, lottoDTO.getFk_lotto_round());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            DBManager.close(connection, statement, null);
        }
    }

}
