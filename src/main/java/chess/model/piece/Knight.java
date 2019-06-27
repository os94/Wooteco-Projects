package chess.model.piece;

import chess.model.Direction;
import chess.model.PlayerType;
import chess.model.Point;

public class Knight extends Piece {
    private static final double SCORE_OF_KNIGHT = 2.5;

    public Knight(PlayerType team, Point point) {
        super(team, point, SCORE_OF_KNIGHT);
    }

    @Override
    public boolean canMove(Direction direction, Point destination) {
        double distance = point.calculateDistance(destination);
        return distance == Math.sqrt(5) && Direction.knightDirection().contains(direction);
    }

    @Override
    public String toString() {
        return "knight";
    }
}
