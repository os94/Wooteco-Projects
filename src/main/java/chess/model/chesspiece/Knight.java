package chess.model.chesspiece;

import chess.model.Direction;
import chess.model.Point;

public class Knight extends Chess {
    public Knight() {
        super(2.5);
    }

    @Override
    public boolean canMove(Point source, Point destination) {
        double xDistance = source.xDistanceFrom(destination);
        double yDistance = source.yDistanceFrom(destination);
        if (Math.abs(xDistance) + Math.abs(yDistance) != 3) {
            return false;
        }
        return !(xDistance == 0) && !(yDistance == 0);
    }
}
