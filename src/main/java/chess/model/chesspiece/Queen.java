package chess.model.chesspiece;

import chess.model.Point;

public class Queen extends Chess {
    public Queen() {
        super(9);
    }

    @Override
    public boolean canMove(Point source, Point destination) {
        Rook rook = new Rook();
        Bishop bishop = new Bishop();
        return rook.canMove(source, destination)
                || bishop.canMove(source, destination);
    }
}
