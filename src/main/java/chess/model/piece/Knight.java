package chess.model.piece;

import chess.model.Direction;
import chess.model.PlayerType;
import chess.model.Point;

public class Knight extends Piece {
    public Knight(PlayerType team, Point point) {
        super(team, point, 2.5);
    }

    @Override
    public Piece createBlack(Point point) {
        return new Knight(PlayerType.BLACK, point);
    }

    @Override
    public Piece createWhite(Point point) {
        return new Knight(PlayerType.WHITE, point);
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
