package chess.model;


import chess.model.chesspiece.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserFactory {
    private static List<chess.model.chesspiece.Chess> defaultChesses = Arrays.asList(
            new Rook(), new Knight(), new Bishop(), new Queen(),
            new King(), new Bishop(), new Knight(), new Rook());

    public static Map<Boolean, User> createUsers() {
        Map<Boolean, User> users = new HashMap<>();
        users.put(true, createWhite());
        users.put(false, createBlack());
        return users;
    }

    private static User createWhite() {
        Map<Point, Chess> chesses = new HashMap<>();
        initializeChesses(chesses, 1);
        initializeWhitePawns(chesses, 2);
        return new User(chesses);
    }

    private static User createBlack() {
        Map<Point, Chess> chesses = new HashMap<>();
        initializeChesses(chesses, 8);
        initializeBlackPawns(chesses, 7);
        return new User(chesses);
    }

    private static void initializeWhitePawns(Map<Point, Chess> chesses, int y) {
        for (int i = 1; i <= 8; i++) {
            chesses.put(Point.of(i, y), new WhitePawn());
        }
    }

    private static void initializeBlackPawns(Map<Point, Chess> chesses, int y) {
        for (int i = 1; i <= 8; i++) {
            chesses.put(Point.of(i, y), new BlackPawn());
        }
    }

    private static void initializeChesses(Map<Point, Chess> chesses, int y) {
        for (int i = 1; i <= 8; i++) {
            chesses.put(Point.of(i, y), defaultChesses.get(i - 1));
        }
    }
}
