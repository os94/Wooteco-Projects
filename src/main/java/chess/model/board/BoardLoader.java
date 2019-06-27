package chess.model.board;

import chess.model.Point;
import chess.model.piece.Piece;

import java.util.HashMap;
import java.util.Map;

public class BoardLoader implements BoardInitializer {
    private final Map<Point, Piece> pieces = new HashMap<>();

    @Override
    public Map<Point, Piece> initialize() {
        return pieces;
    }

    public void add(Point point, Piece piece) {
        pieces.put(point, piece);
    }
}
