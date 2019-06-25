package chess.model;

import chess.model.piece.Piece;

import java.util.Map;

public interface BoardInitializer {
    Map<Point, Piece> initialize();
}
