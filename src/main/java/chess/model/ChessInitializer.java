package chess.model;

import chess.model.piece.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class ChessInitializer implements BoardInitializer {
    private final Map<Point, Piece> chessBoard = new HashMap<>();

    @Override
    public Map<Point, Piece> initialize() {
        createChessPieces(PlayerType.WHITE);
        createPawn(PlayerType.WHITE);
        createChessPieces(PlayerType.BLACK);
        createPawn(PlayerType.BLACK);

        return chessBoard;
    }

    private void createChessPieces(PlayerType team) {
        List<BiFunction<PlayerType, Point, Piece>> pieces =
                Arrays.asList(Rook::new, King::new, Bishop::new, King::new,
                        Queen::new, Bishop::new, Knight::new, Rook::new);
        int pieceYPoint = team == PlayerType.WHITE ? 1 : 8;
        for (int i = 1; i <= 8; i++) {
            chessBoard.put(Point.of(i, pieceYPoint), pieces.get(i - 1).apply(team, Point.of(i, pieceYPoint)));
        }
    }

    private void createPawn(PlayerType team) {
        int pawnYPoint = team == PlayerType.WHITE ? 2 : 7;
        for (int i = 1; i <= 8; i++) {
            Point point = Point.of(i, pawnYPoint);
            chessBoard.put(point, new Pawn(team, point));
        }
    }
}
