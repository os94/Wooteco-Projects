package chess.model.chesspiece;

import chess.model.Point;

public class WhitePawn extends Chess {
    public WhitePawn() {
        super(1);
    }

    @Override
    public boolean canMove(Point source, Point destination) {
        if (destination.equals(source.next(0, 2))
            && source.getY() != 2) {
            return false;
        }
        return destination.equals(source.next(-1, 1))
                || destination.equals(source.next(1, 1))
                || destination.equals(source.next(0, 1));
    }
}
