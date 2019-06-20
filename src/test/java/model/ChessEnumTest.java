package model;

import chess.model.ChessEnum;
import chess.model.Point;
import chess.model.User;
import chess.model.UserFactory;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ChessEnumTest {
    @Test
    void King_움직일수없는_경우() {
        assertThat(ChessEnum.KING.canMove(Point.of(6, 5), Point.of(6, 7), null)).isFalse();
    }

    @Test
    void King_움직일수있는_경우() {
        assertThat(ChessEnum.KING.canMove(Point.of(6, 5), Point.of(6, 6), null)).isTrue();
        assertThat(ChessEnum.KING.canMove(Point.of(6, 5), Point.of(7, 6), null)).isTrue();
    }

    @Test
    void Rook_움직일수없는_경우() {
        assertThat(ChessEnum.ROOK.canMove(Point.of(4, 5), Point.of(5, 6), null)).isFalse();
    }

    @Test
    void Rook_움직일수있는_경우() {
        assertThat(ChessEnum.ROOK.canMove(Point.of(4, 5), Point.of(4, 6), null)).isTrue();
        assertThat(ChessEnum.ROOK.canMove(Point.of(4, 5), Point.of(3, 5), null)).isTrue();
    }

    @Test
    void Bishop_움직일수없는_경우() {
        assertThat(ChessEnum.BISHOP.canMove(Point.of(4, 5), Point.of(5, 5), null)).isFalse();
    }

    @Test
    void Bishop_움직일수있는_경우() {
        assertThat(ChessEnum.BISHOP.canMove(Point.of(4, 5), Point.of(5, 6), null)).isTrue();
        assertThat(ChessEnum.BISHOP.canMove(Point.of(4, 5), Point.of(5, 4), null)).isTrue();
    }

    @Test
    void Knight_움직일수없는_경우() {
        assertThat(ChessEnum.KNIGHT.canMove(Point.of(4, 4), Point.of(4, 7), null)).isFalse();
        assertThat(ChessEnum.KNIGHT.canMove(Point.of(4, 4), Point.of(4, 5), null)).isFalse();
    }

    @Test
    void Knight_움직일수있는_경우() {
        assertThat(ChessEnum.KNIGHT.canMove(Point.of(4, 4), Point.of(6, 5), null)).isTrue();
        assertThat(ChessEnum.KNIGHT.canMove(Point.of(4, 4), Point.of(5, 6), null)).isTrue();
    }

    @Test
    void Pawn_한칸_전진() {
        assertThat(ChessEnum.Pawn.canMove(Point.of(5, 2), Point.of(5, 3), UserFactory.createUsers())).isTrue();
    }

    @Test
    void Pawn_두칸_전진() {
        assertThat(ChessEnum.Pawn.canMove(Point.of(5, 2), Point.of(5, 4), UserFactory.createUsers())).isTrue();
    }

    @Test
    void Pawn_두칸_전진_실패() {
        Map<Point, ChessEnum> whiteChess = new HashMap<>();
        whiteChess.put(Point.of(5, 3), ChessEnum.Pawn);
        User white = new User(whiteChess);

        Map<Boolean, User> users = new HashMap<>();
        users.put(true, white);

        assertThat(ChessEnum.Pawn.canMove(Point.of(5, 3), Point.of(5, 5), users)).isFalse();
    }

    /*@Test
    void Pawn_대각선_전진() {
        Map<Point, ChessEnum> whiteChess = new HashMap<>();
        whiteChess.put(Point.of(3, 6), ChessEnum.Pawn);
        User white = new User(whiteChess);

        Map<Point, ChessEnum> blackChess = new HashMap<>();
        blackChess.put(Point.of(2, 7), ChessEnum.KING);
        User black = new User(blackChess);

        Map<Boolean, User> users = new HashMap<>();
        users.put(true, white);
        users.put(false, black);

        assertThat(ChessEnum.Pawn.canMove(Point.of(3, 6), Point.of(2, 7), users)).isTrue();
    }*/

    @Test
    void Pawn_대각선_전진_실패() {
        assertThat(ChessEnum.Pawn.canMove(Point.of(5, 2), Point.of(6, 3), UserFactory.createUsers())).isFalse();
    }
}
