package chess.db;

import chess.model.PlayerType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TurnDao {
    private static final String SELECT_CURRENT_TURN_BY_ROUND = "select current_team from turn where round = ?";
    private static final String INSERT_FIRST_TURN_BY_ROUND = "insert into turn (current_team, round) values (?, ?)";
    private static final String UPDATE_CURRENT_TURN_BY_ROUND = "update turn set current_team = ? where round = ?";
    private final Connection connection;

    public TurnDao(Connection connection) {
        this.connection = connection;
    }

    public void addFirstTurn(int round) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(INSERT_FIRST_TURN_BY_ROUND);
        pstmt.setString(1, "WHITE");
        pstmt.setInt(2, round);
        pstmt.executeUpdate();
    }

    public String selectCurrentTurn(int round) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(SELECT_CURRENT_TURN_BY_ROUND);
        pstmt.setInt(1, round);
        ResultSet resultSet = pstmt.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("current_team");
        }
        return "WHITE";
    }

    public void updateCurrentTurn(int round) throws SQLException {
        String currentTeam = selectCurrentTurn(round);
        currentTeam = changeTeam(currentTeam);

        PreparedStatement pstmt = connection.prepareStatement(UPDATE_CURRENT_TURN_BY_ROUND);
        pstmt.setString(1, currentTeam);
        pstmt.setInt(2, round);
        pstmt.executeUpdate();
    }

    private String changeTeam(String currentTeam) {
        if ("WHITE".equals(currentTeam)) {
            return  "BLACK";
        }
        return  "WHITE";
    }
}
