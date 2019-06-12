package lotto.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoundDAO {
    public int getRoundNo() throws SQLException {
        String query = "SELECT max(round) FROM round_tb";
        PreparedStatement statement = LottoGameDAO.getConnection().prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        if (!resultSet.next()) {
            return 0;
        }
        return resultSet.getInt("max(round)") + 1;
    }
}
