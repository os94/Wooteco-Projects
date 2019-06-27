package chess.model.board;

import chess.model.PlayerType;
import chess.model.Point;
import chess.model.piece.Piece;
import chess.model.piece.PieceFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessInitializer implements BoardInitializer {
    private static final int FIRST_LINE_OF_WHITE = 1;
    private static final int SECOND_LINE_OF_WHITE = 2;
    private static final int FIRST_LINE_OF_BLACK = 8;
    private static final int SECOND_LINE_OF_BLACK = 7;
    private static final int SIZE_OF_CHESS_BOARD = 8;

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
        List<String> pieces =
                Arrays.asList("rook", "knight", "bishop", "queen",
                        "king", "bishop", "knight", "rook");
        int pieceYPoint = team == PlayerType.WHITE ? FIRST_LINE_OF_WHITE : FIRST_LINE_OF_BLACK;
        for (int i = 1; i <= SIZE_OF_CHESS_BOARD; i++) {
            Point point = Point.of(i, pieceYPoint);
            chessBoard.put(point, PieceFactory.create(pieces.get(i - 1), team, point));
        }
    }

    private void createPawn(PlayerType team) {
        int pawnYPoint = team == PlayerType.WHITE ? SECOND_LINE_OF_WHITE : SECOND_LINE_OF_BLACK;
        for (int i = 1; i <= SIZE_OF_CHESS_BOARD; i++) {
            Point point = Point.of(i, pawnYPoint);
            chessBoard.put(point, PieceFactory.create("pawn", team, point));
        }
    }
}
