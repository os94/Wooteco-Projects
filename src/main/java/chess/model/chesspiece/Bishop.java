package chess.model.chesspiece;

import chess.model.Point;

public class Bishop extends Chess {
    public Bishop() {
        super(3);
    }

    @Override
    public boolean canMove(Point source, Point destination) {
        double xDistance = source.xDistanceFrom(destination);
        double yDistance = source.yDistanceFrom(destination);
        double slope = yDistance / xDistance;

        return Math.abs(slope) == 1;
    }
}
