package chess.model.piece;

import chess.model.Direction;
import chess.model.PlayerType;
import chess.model.Point;

public class Queen extends Piece {
    public Queen(PlayerType team, Point point) {
        super(team, point, 9);
    }

    @Override
    public Piece createBlack(Point point) {
        return new Queen(PlayerType.BLACK, point);
    }

    @Override
    public Piece createWhite(Point point) {
        return new Queen(PlayerType.WHITE, point);
    }

    @Override
    public boolean canMove(Direction direction, Point destination) {
        return Direction.rookDirection().contains(direction)
                || Direction.bishopDirection().contains(direction);
    }

    @Override
    public String toString() {
        return "queen";
    }
}
