package chess.model.chesspiece;

import chess.model.Point;

public class Rook extends Chess {
    public Rook() {
        super(5);
    }

    @Override
    public boolean canMove(Point source, Point destination) {
        return source.xDistanceFrom(destination) == 0
                || source.yDistanceFrom(destination) == 0;
    }
}
