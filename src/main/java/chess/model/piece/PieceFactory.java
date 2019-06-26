package chess.model.piece;

import chess.model.PlayerType;
import chess.model.Point;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class PieceFactory {
    private static final Map<String, BiFunction<PlayerType, Point, Piece>> factory = new HashMap<>();

    static {
        factory.put("king", King::new);
        factory.put("queen", Queen::new);
        factory.put("bishop", Bishop::new);
        factory.put("rook", Rook::new);
        factory.put("knight", Knight::new);
        factory.put("pawn", Pawn::new);
    }

    public static Piece create(String pieceName, PlayerType team, Point point) {
        return factory.get(pieceName.toLowerCase()).apply(team, point);
    }
}
