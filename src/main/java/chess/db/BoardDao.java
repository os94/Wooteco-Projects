package chess.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardDao {
    private static final String INSERT_BOARD_SQL = "INSERT INTO board(piece,team,point,round) values(?, ?, ?, ?)";
    private static final String SELECT_CURRENT_ROUND = "select round from board order by round desc limit 1";
    private static final String SELECT_CHESSES = "select piece, team, point from board where round = ?";
    private static final String DELETE_PIECE = "delete from board where round = ? and point = ?";
    private static final String UPDATE_PIECE = "update board set point = ? where point = ? and round = ?";

    private static BoardDao INSTANCE = null;

    private BoardDao() {
    }

    public static BoardDao getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BoardDao();
        }
        return INSTANCE;
    }

    public void initialize(List<List<Object>> boardDtos) {
        JDBCTemplate.getInstance().updateBatchQuery(INSERT_BOARD_SQL, boardDtos);
    }

    public int recentRound() throws SQLException {
        List<Map<String, Object>> results = JDBCTemplate.getInstance().selectQuery(SELECT_CURRENT_ROUND);
        return makeRecentRound(results);
    }

    private int makeRecentRound(List<Map<String, Object>> results) {
        if (results.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(String.valueOf(results.get(0).getOrDefault("round", 0)));
    }

    public List<BoardDto> findChesses(int round) throws SQLException {
        List<Map<String, Object>> results = JDBCTemplate.getInstance().selectQuery(SELECT_CHESSES, round);
        return makeChesses(results, round);
    }

    private List<BoardDto> makeChesses(List<Map<String, Object>> results, int round) {
        List<BoardDto> chesses = new ArrayList<>();

        for (Map<String, Object> result : results) {
            chesses.add(new BoardDto(
                    (String) result.getOrDefault("piece", "BLANK"),
                    (String) result.getOrDefault("team", "NONE"),
                    (String) result.getOrDefault("point", "0"),
                    round));
        }
        return chesses;
    }

    public void remove(int round, String destination) {
        JDBCTemplate.getInstance().updateQuery(DELETE_PIECE, round, destination);
    }

    public void update(int round, String source, String destination) {
        JDBCTemplate.getInstance().updateQuery(UPDATE_PIECE, destination, source, round);
    }
}
