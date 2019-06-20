package chess.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserFactory {
    private static List<Chess> defaultChesses = Arrays.asList(
            Chess.ROOK, Chess.KNIGHT, Chess.BISHOP, Chess.QUEEN,
            Chess.KING, Chess.BISHOP, Chess.KNIGHT, Chess.ROOK);

    public static Map<Boolean, User> createUsers() {
        Map<Boolean, User> users = new HashMap<>();
        users.put(true, createWhite());
        users.put(false, createBlack());
        return users;
    }

    private static User createWhite() {
        Map<Point, Chess> chesses = new HashMap<>();
        initializeChesses(chesses, 1);
        initializePawns(chesses, 2);
        return new User(chesses);
    }

    private static User createBlack() {
        Map<Point, Chess> chesses = new HashMap<>();
        initializeChesses(chesses, 8);
        initializePawns(chesses, 7);
        return new User(chesses);
    }

    private static void initializePawns(Map<Point, Chess> chesses, int y) {
        for (int i = 1; i <= 8; i++) {
            chesses.put(Point.of(i, y), Chess.Pawn);
        }
    }

    private static void initializeChesses(Map<Point, Chess> chesses, int y) {
        for (int i = 1; i <= 8; i++) {
            chesses.put(Point.of(i, y), defaultChesses.get(i - 1));
        }
    }
}
