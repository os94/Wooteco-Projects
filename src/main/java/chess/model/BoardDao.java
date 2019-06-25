package chess.model;

import chess.DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BoardDao {
    private static final String INSERT_BOARD_SQL = "INSERT INTO board(piece,team,point,round) values(?,?,?,?)";
    private static final String SELECT_CURRENT_ROUND = "select round from board order by round desc limit 1";

    private final Connection connection;

    public BoardDao(Connection connection) {
        this.connection = connection;
    }

    public void delete() throws SQLException {
        PreparedStatement pstmt;

        String sql = "delete from board";
        pstmt = connection.prepareStatement(sql);
        pstmt.executeUpdate();

        DBManager.close(pstmt);
    }

    public void initialize(List<BoardDto> boardDtos) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(INSERT_BOARD_SQL);
        for (BoardDto boardDto : boardDtos) {
            pstmt.setString(1, boardDto.getPiece());
            pstmt.setString(2, boardDto.getTeam());
            pstmt.setString(3, boardDto.getPoint());
            pstmt.setInt(4, boardDto.getRound());
            pstmt.addBatch();
        }
        pstmt.executeBatch();
    }

    public int recentRound() throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(SELECT_CURRENT_ROUND);
        ResultSet resultSet = pstmt.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("round");
        }
        return 0;
    }
}
