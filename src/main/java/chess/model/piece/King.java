package chess.model.piece;

import chess.model.Direction;
import chess.model.PlayerType;
import chess.model.Point;

public class King extends Piece {
    public King(PlayerType team, Point point) {
        super(team, point, 0);
    }

    @Override
    public boolean canMove(Direction direction, Point destination) {
        double distance = point.calculateDistance(destination);
        return distance <= Math.sqrt(2) && Direction.allDirection().contains(direction);
    }

    @Override
    public Piece createBlack(Point point) {
        return new King(PlayerType.BLACK, point);
    }

    @Override
    public Piece createWhite(Point point) {
        return new King(PlayerType.WHITE, point);
    }
}
