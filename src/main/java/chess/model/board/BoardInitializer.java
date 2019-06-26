package chess.model.board;

import chess.model.Point;
import chess.model.piece.Piece;

import java.util.Map;

public interface BoardInitializer {
    Map<Point, Piece> initialize();
}
