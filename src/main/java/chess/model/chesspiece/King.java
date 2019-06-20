package chess.model.chesspiece;

import chess.model.Point;

public class King extends Chess {
    public King() {
        super(0);
    }

    @Override
    public boolean canMove(Point source, Point destination) {
        double xDistance = source.xDistanceFrom(destination);
        double yDistance = source.yDistanceFrom(destination);
        double distance = Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));

        return distance == 1 || distance == Math.sqrt(2);
    }
}
