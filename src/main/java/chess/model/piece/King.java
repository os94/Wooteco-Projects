package chess.model.piece;

import chess.model.Direction;
import chess.model.PlayerType;
import chess.model.Point;

public class King extends Piece {
    @Override
    public boolean isPossibleDirection(Direction direction, Point destination) {
        return false;
    }

    @Override
    public boolean canMove(Direction direction, Piece destinationPiece) {
        return false;
    }

    private King(PlayerType team, Point point) {
        super(team, point);
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
