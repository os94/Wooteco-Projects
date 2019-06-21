package chess.model.piece;

import chess.model.Direction;
import chess.model.PlayerType;
import chess.model.Point;

import java.util.List;

public class Pawn extends Piece {
    private boolean isFirstMove = true;

    public Pawn(PlayerType team, Point point) {
        super(team, point);
    }

    @Override
    public void move(Point destination) {
        super.move(destination);
        isFirstMove = false;
    }

    @Override
    public boolean canMove(Direction direction, Point destination) {
        List<Direction> pawnDirections = Direction.pawnDirection(team);
        double distance = point.calculateDistance(destination);
        if (distance == 2) {
            return pawnDirections.get(0) == direction && isFirstMove;
        }
        if (distance == 1) {
            return pawnDirections.get(0) == direction;
        }
        if (distance == Math.sqrt(2)) {
            return pawnDirections.get(1) == direction || pawnDirections.get(2) == direction;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public boolean isAvailableDestinationOfPawn(Direction direction, Piece destinationPiece) {
        List<Direction> pawnDirections = Direction.pawnDirection(team);
        if (direction == pawnDirections.get(0))
            if (!destinationPiece.isNone()) {
                return false;
            }
        if (direction == pawnDirections.get(1) || direction == pawnDirections.get(2)) {
            return destinationPiece.isOpponent(team);
        }
        return true;
    }

    @Override
    public Piece createBlack(Point point) {
        return new Pawn(PlayerType.BLACK, point);
    }

    @Override
    public Piece createWhite(Point point) {
        return new Pawn(PlayerType.WHITE, point);
    }
}
