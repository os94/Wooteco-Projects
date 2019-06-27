package chess.model.piece;

import chess.model.Direction;
import chess.model.PlayerType;
import chess.model.Point;

public abstract class Piece {
    final PlayerType team;
    Point point;
    private final double score;

    Piece(PlayerType team, Point point, double score) {
        this.team = team;
        this.point = point;
        this.score = score;
    }

    public abstract boolean canMove(Direction direction, Point destination);

    public boolean isSameTeam(PlayerType team) {
        return this.team == team;
    }

    public boolean isOpponent(PlayerType team) {
        return this.team == team.toggle();
    }

    public boolean isNone() {
        return team == PlayerType.NONE;
    }

    public boolean isKing() {
        return false;
    }

    public boolean isPawn() {
        return false;
    }

    public boolean isAvailableDestinationOfPawn(Direction direction, Piece destinationPiece) {
        return true;
    }

    public void move(Point destination) {
        point = destination;
    }

    public double getScore() {
        return score;
    }

    public int getX() {
        return point.getX();
    }

    public String getTeam() {
        return team.name();
    }
}
