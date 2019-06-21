package chess.model.piece;

import chess.model.Direction;
import chess.model.PlayerType;
import chess.model.Point;

public class Blank extends Piece {
    private static final String ERROR_MESSAGE_BLANK = "Blank 호출 오류";

    public Blank(PlayerType team, Point point) {
        super(team, point);
    }

    @Override
    public Piece createBlack(Point point) {
        throw new IllegalArgumentException(ERROR_MESSAGE_BLANK);
    }

    @Override
    public Piece createWhite(Point point) {
        throw new IllegalArgumentException(ERROR_MESSAGE_BLANK);
    }

    @Override
    public boolean isPossibleDirection(Direction direction, Point destination) {
        throw new IllegalArgumentException(ERROR_MESSAGE_BLANK);
    }

    @Override
    public boolean canMove(Direction direction, Piece destinationPiece) {
        throw new IllegalArgumentException(ERROR_MESSAGE_BLANK);
    }
}
