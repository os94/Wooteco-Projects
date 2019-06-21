package chess.model.piece;

import chess.model.Point;

public abstract class Piece {
    String team;
    Point point;

    Piece(String team, Point point) {
        this.team = team;
        this.point = point;
    }

    public abstract Piece createBlack(Point point);

    public abstract Piece createWhite(Point point);
}
