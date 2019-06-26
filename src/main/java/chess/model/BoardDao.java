package chess.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDao {
    private static final String INSERT_BOARD_SQL = "INSERT INTO board(piece,team,point,round) values(?,?,?,?)";
    private static final String SELECT_CURRENT_ROUND = "select round from board order by round desc limit 1";
    private static final String SELECT_CURRENT_TEAM = "select current_team from team";
    private static final String SELECT_CHESSES = "select piece, team, point from board where round = ?";
    private static final String DELETE_PIECE = "delete from board where round = ? and point = ?";
    private static final String UPDATE_PIECE = "update board set point = ? where point = ? and round = ?";

    private final Connection connection;

    public BoardDao(Connection connection) {
        this.connection = connection;
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

    public String currentTeam() throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(SELECT_CURRENT_TEAM);
        ResultSet resultSet = pstmt.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("current_team");
        }
        return "WHITE";
    }

    public List<BoardDto> findChesses(int round) throws SQLException {
        List<BoardDto> chesses = new ArrayList<>();
        PreparedStatement pstmt = connection.prepareStatement(SELECT_CHESSES);
        pstmt.setInt(1, round);
        ResultSet resultSet = pstmt.executeQuery();
        while (resultSet.next()) {
            String piece = resultSet.getString("piece");
            String team = resultSet.getString("team");
            String point = resultSet.getString("point");
            chesses.add(new BoardDto(piece, team, point, round));
        }
        return chesses;
    }

    public void remove(int round, String destination) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(DELETE_PIECE);
        pstmt.setInt(1, round);
        pstmt.setString(2, destination);
        pstmt.executeUpdate();
    }

    public void update(int round, String source, String destination) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(UPDATE_PIECE);
        pstmt.setString(1, destination);
        pstmt.setString(2, source);
        pstmt.setInt(3, round);
        pstmt.executeUpdate();
    }
}
