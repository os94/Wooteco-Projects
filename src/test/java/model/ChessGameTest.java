package model;

import chess.model.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChessGameTest {
    private ChessGame chessGame;

    @Test
    void King을_잡을_경우_true_반환() {
        Map<Point, ChessEnum> whiteChess = new HashMap<>();
        whiteChess.put(Point.of(4, 5), ChessEnum.ROOK);
        User white = new User(whiteChess);

        Map<Point, ChessEnum> blackChess = new HashMap<>();
        blackChess.put(Point.of(4, 8), ChessEnum.KING);
        User black = new User(blackChess);

        Map<Boolean, User> users = new HashMap<>();
        users.put(true, white);
        users.put(false, black);

        chessGame = new ChessGame(users);
        boolean isKingDie = chessGame.move(Point.of(4, 5), Point.of(4, 8));
        assertThat(isKingDie).isTrue();
    }

    @Test
    void Rook_이동경로상에_장애물이_존재하는_경우() {
        chessGame = new ChessGame(UserFactory.createUsers());
        assertThrows(IllegalArgumentException.class, () -> {
            chessGame.move(Point.of(1, 1), Point.of(1, 3));
        });
    }

    @Test
    void Bishop_이동경로상에_장애물이_존재하는_경우() {
        chessGame = new ChessGame(UserFactory.createUsers());
        assertThrows(IllegalArgumentException.class, () -> {
            chessGame.move(Point.of(3, 1), Point.of(5, 3));
        });
    }
}
