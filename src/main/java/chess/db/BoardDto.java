package chess.db;

import java.util.Arrays;
import java.util.List;

public class BoardDto {
    private final String piece;
    private final String team;
    private final String point;
    private final int round;

    public BoardDto(String piece, String team, String point, int round) {
        this.piece = piece;
        this.team = team;
        this.point = point;
        this.round = round;
    }

    public String getPiece() {
        return piece;
    }

    public String getTeam() {
        return team;
    }

    public String getPoint() {
        return point;
    }

    public List<Object> getAll() {
        return Arrays.asList(piece, team, point, round);
    }
}
