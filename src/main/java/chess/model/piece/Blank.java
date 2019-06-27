package chess.model.piece;

import chess.model.Direction;
import chess.model.PlayerType;
import chess.model.Point;

public class Blank extends Piece {
    private static final double SCORE_OF_BLACK = 0;
    private static final String ERROR_MESSAGE_BLANK = "Blank 호출 오류";

    public Blank(PlayerType team, Point point) {
        super(team, point, SCORE_OF_BLACK);
    }

    @Override
    public boolean canMove(Direction direction, Point destination) {
        throw new IllegalArgumentException(ERROR_MESSAGE_BLANK);
    }

    @Override
    public boolean isAvailableDestinationOfPawn(Direction direction, Piece destinationPiece) {
        throw new IllegalArgumentException(ERROR_MESSAGE_BLANK);
    }

}
