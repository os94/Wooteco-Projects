package model;

import chess.model.Chess;
import chess.model.Point;
import chess.model.User;
import chess.model.UserFactory;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ChessTest {
    @Test
    void King_움직일수없는_경우() {
        assertThat(Chess.KING.canMove(Point.of(6, 5), Point.of(6, 7), null)).isFalse();
    }

    @Test
    void King_움직일수있는_경우() {
        assertThat(Chess.KING.canMove(Point.of(6, 5), Point.of(6, 6), null)).isTrue();
        assertThat(Chess.KING.canMove(Point.of(6, 5), Point.of(7, 6), null)).isTrue();
    }

    @Test
    void Rook_움직일수없는_경우() {
        assertThat(Chess.ROOK.canMove(Point.of(4, 5), Point.of(5, 6), null)).isFalse();
    }

    @Test
    void Rook_움직일수있는_경우() {
        assertThat(Chess.ROOK.canMove(Point.of(4, 5), Point.of(4, 6), null)).isTrue();
        assertThat(Chess.ROOK.canMove(Point.of(4, 5), Point.of(3, 5), null)).isTrue();
    }

    @Test
    void Bishop_움직일수없는_경우() {
        assertThat(Chess.BISHOP.canMove(Point.of(4, 5), Point.of(5, 5), null)).isFalse();
    }

    @Test
    void Bishop_움직일수있는_경우() {
        assertThat(Chess.BISHOP.canMove(Point.of(4, 5), Point.of(5, 6), null)).isTrue();
        assertThat(Chess.BISHOP.canMove(Point.of(4, 5), Point.of(5, 4), null)).isTrue();
    }

    @Test
    void Knight_움직일수없는_경우() {
        assertThat(Chess.KNIGHT.canMove(Point.of(4, 4), Point.of(4, 7), null)).isFalse();
        assertThat(Chess.KNIGHT.canMove(Point.of(4, 4), Point.of(4, 5), null)).isFalse();
    }

    @Test
    void Knight_움직일수있는_경우() {
        assertThat(Chess.KNIGHT.canMove(Point.of(4, 4), Point.of(6, 5), null)).isTrue();
        assertThat(Chess.KNIGHT.canMove(Point.of(4, 4), Point.of(5, 6), null)).isTrue();
    }

    @Test
    void Pawn_한칸_전진() {
        assertThat(Chess.Pawn.canMove(Point.of(5, 2), Point.of(5, 3), UserFactory.createUsers())).isTrue();
    }

    @Test
    void Pawn_두칸_전진() {
        assertThat(Chess.Pawn.canMove(Point.of(5, 2), Point.of(5, 4), UserFactory.createUsers())).isTrue();
    }

    @Test
    void Pawn_두칸_전진_실패() {
        Map<Point, Chess> whiteChess = new HashMap<>();
        whiteChess.put(Point.of(5, 3), Chess.Pawn);
        User white = new User(whiteChess);

        Map<Boolean, User> users = new HashMap<>();
        users.put(true, white);

        assertThat(Chess.Pawn.canMove(Point.of(5, 3), Point.of(5, 5), users)).isFalse();
    }

    /*@Test
    void Pawn_대각선_전진() {
        Map<Point, Chess> whiteChess = new HashMap<>();
        whiteChess.put(Point.of(3, 6), Chess.Pawn);
        User white = new User(whiteChess);

        Map<Point, Chess> blackChess = new HashMap<>();
        blackChess.put(Point.of(2, 7), Chess.KING);
        User black = new User(blackChess);

        Map<Boolean, User> users = new HashMap<>();
        users.put(true, white);
        users.put(false, black);

        assertThat(Chess.Pawn.canMove(Point.of(3, 6), Point.of(2, 7), users)).isTrue();
    }*/

    @Test
    void Pawn_대각선_전진_실패() {
        assertThat(Chess.Pawn.canMove(Point.of(5, 2), Point.of(6, 3), UserFactory.createUsers())).isFalse();
    }
}
