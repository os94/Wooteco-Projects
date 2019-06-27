package chess.db;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class TurnDao {
    private static final String SELECT_CURRENT_TURN_BY_ROUND = "select current_team from turn where round = ?";
    private static final String INSERT_FIRST_TURN_BY_ROUND = "insert into turn (current_team, round) values (?, ?)";
    private static final String UPDATE_CURRENT_TURN_BY_ROUND = "update turn set current_team = ? where round = ?";
    private static final String TEAM_WHITE = "WHITE";
    private static final String TEAM_BLACK = "BLACK";

    private static TurnDao INSTANCE = null;

    private TurnDao() {
    }

    public static TurnDao getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TurnDao();
        }
        return INSTANCE;
    }

    public void addFirstTurn(int round) {
        JDBCTemplate.getInstance().updateQuery(INSERT_FIRST_TURN_BY_ROUND, TEAM_WHITE, round);
    }

    public String selectCurrentTurn(int round) throws SQLException {
        List<Map<String, Object>> results = JDBCTemplate.getInstance().selectQuery(SELECT_CURRENT_TURN_BY_ROUND, round);
        return makeCurrentTurn(results);
    }

    private String makeCurrentTurn(List<Map<String, Object>> results) {
        if (results.isEmpty()) {
            return TEAM_WHITE;
        }
        return String.valueOf(results.get(0).getOrDefault("current_team", TEAM_WHITE));
    }

    public void updateCurrentTurn(int round) throws SQLException {
        String currentTeam = selectCurrentTurn(round);
        currentTeam = changeTeam(currentTeam);

        JDBCTemplate.getInstance().updateQuery(UPDATE_CURRENT_TURN_BY_ROUND, currentTeam, round);
    }

    private String changeTeam(String currentTeam) {
        if (TEAM_WHITE.equals(currentTeam)) {
            return TEAM_BLACK;
        }
        return TEAM_WHITE;
    }
}
