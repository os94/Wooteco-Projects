package chess.model.piece;

import chess.model.Direction;
import chess.model.Point;

public class Pawn extends Piece {
    private boolean isFirstMove = true;

    private Pawn(String team, Point point) {
        super(team, point);
    }

    public boolean canMove(Point destination) {
        double distance = point.calculateDistance(destination);
        Direction direction = Direction.valueOf(point.xDistanceFrom(destination), point.yDistanceFrom(destination));
        if (Direction.NORTH == direction) {
            return distance == 1 || distance == 2;
        }
        if (Direction.NORTHEAST == direction || Direction.NORTHWEST == direction) {
            return distance == Math.sqrt(2);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Piece createBlack(Point point) {
        return new Pawn("black", point);
    }

    @Override
    public Piece createWhite(Point point) {
        return new Pawn("white", point);
    }
}
