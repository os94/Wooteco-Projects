package chess.model.piece;

import chess.model.Direction;
import chess.model.PlayerType;
import chess.model.Point;

public class King extends Piece {
    private static final double SCORE_OF_KING = 0;

    public King(PlayerType team, Point point) {
        super(team, point, SCORE_OF_KING);
    }

    @Override
    public boolean canMove(Direction direction, Point destination) {
        double distance = point.calculateDistance(destination);
        return distance <= Math.sqrt(2) && Direction.allDirection().contains(direction);
    }

    @Override
    public boolean isKing() {
        return true;
    }

    @Override
    public String toString() {
        return "king";
    }
}
