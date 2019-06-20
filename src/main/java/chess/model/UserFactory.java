package chess.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserFactory {
    private static List<ChessEnum> defaultChesses = Arrays.asList(
            ChessEnum.ROOK, ChessEnum.KNIGHT, ChessEnum.BISHOP, ChessEnum.QUEEN,
            ChessEnum.KING, ChessEnum.BISHOP, ChessEnum.KNIGHT, ChessEnum.ROOK);

    public static Map<Boolean, User> createUsers() {
        Map<Boolean, User> users = new HashMap<>();
        users.put(true, createWhite());
        users.put(false, createBlack());
        return users;
    }

    private static User createWhite() {
        Map<Point, ChessEnum> chesses = new HashMap<>();
        initializeChesses(chesses, 1);
        initializePawns(chesses, 2);
        return new User(chesses);
    }

    private static User createBlack() {
        Map<Point, ChessEnum> chesses = new HashMap<>();
        initializeChesses(chesses, 8);
        initializePawns(chesses, 7);
        return new User(chesses);
    }

    private static void initializePawns(Map<Point, ChessEnum> chesses, int y) {
        for (int i = 1; i <= 8; i++) {
            chesses.put(Point.of(i, y), ChessEnum.Pawn);
        }
    }

    private static void initializeChesses(Map<Point, ChessEnum> chesses, int y) {
        for (int i = 1; i <= 8; i++) {
            chesses.put(Point.of(i, y), defaultChesses.get(i - 1));
        }
    }
}
