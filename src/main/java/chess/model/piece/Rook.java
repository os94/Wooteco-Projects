package chess.model.piece;

import chess.model.Direction;
import chess.model.PlayerType;
import chess.model.Point;

public class Rook extends Piece {
    public Rook(PlayerType team, Point point) {
        super(team, point);
    }

    @Override
    public Piece createBlack(Point point) {
        return new Rook(PlayerType.BLACK, point);
    }

    @Override
    public Piece createWhite(Point point) {
        return new Rook(PlayerType.WHITE, point);
    }

    @Override
    public boolean canMove(Direction direction, Point destination) {
        return Direction.rookDirection().contains(direction);
    }
}
