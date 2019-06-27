package chess.model.piece;

import chess.model.Direction;
import chess.model.PlayerType;
import chess.model.Point;

public class Bishop extends Piece {
    private static final double SCORE_OF_BISHOP = 3;

    public Bishop(PlayerType team, Point point) {
        super(team, point, SCORE_OF_BISHOP);
    }

    @Override
    public boolean canMove(Direction direction, Point destination) {
        return Direction.bishopDirection().contains(direction);
    }

    @Override
    public String toString() {
        return "bishop";
    }
}
