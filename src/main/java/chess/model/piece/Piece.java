package chess.model.piece;

import chess.model.Direction;
import chess.model.PlayerType;
import chess.model.Point;

public abstract class Piece {
    PlayerType team;
    Point point;
    int score;

    Piece(PlayerType team, Point point) {
        this.team = team;
        this.point = point;
    }

    public abstract Piece createBlack(Point point);

    public abstract Piece createWhite(Point point);

    public boolean isSameTeam(PlayerType team){
        return this.team == team;
    }

    public boolean isOpponent(PlayerType team) {
        return this.team == team.toggle();
    }

    public boolean isNone() {
        return team == PlayerType.NONE;
    }

    public abstract boolean isPossibleDirection(Direction direction, Point destination);

    public abstract boolean canMove(Direction direction, Piece destinationPiece);
}
