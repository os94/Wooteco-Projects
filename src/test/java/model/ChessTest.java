package model;

import chess.model.Chess;
import chess.model.Point;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ChessTest {
    @Test
    void King_움직일수없는_경우() {
        assertThat(Chess.KING.canMove(Point.of(6, 5), Point.of(6, 7))).isFalse();
    }

    @Test
    void King_움직일수있는_경우() {
        assertThat(Chess.KING.canMove(Point.of(6, 5), Point.of(6, 6))).isTrue();
        assertThat(Chess.KING.canMove(Point.of(6, 5), Point.of(7, 6))).isTrue();
    }

    @Test
    void Rook_움직일수없는_경우() {
        assertThat(Chess.ROOK.canMove(Point.of(4, 5), Point.of(5, 6))).isFalse();
    }

    @Test
    void Rook_움직일수있는_경우() {
        assertThat(Chess.ROOK.canMove(Point.of(4, 5), Point.of(4, 6))).isTrue();
        assertThat(Chess.ROOK.canMove(Point.of(4, 5), Point.of(3, 5))).isTrue();
    }

    @Test
    void Bishop_움직일수없는_경우() {
        assertThat(Chess.BISHOP.canMove(Point.of(4, 5), Point.of(5, 5))).isFalse();
    }

    @Test
    void Bishop_움직일수있는_경우() {
        assertThat(Chess.BISHOP.canMove(Point.of(4, 5), Point.of(5, 6))).isTrue();
        assertThat(Chess.BISHOP.canMove(Point.of(4, 5), Point.of(5, 4))).isTrue();
    }

    @Test
    void Knight_움직일수없는_경우() {
        assertThat(Chess.KNIGHT.canMove(Point.of(4, 4), Point.of(4, 7))).isFalse();
        assertThat(Chess.KNIGHT.canMove(Point.of(4, 4), Point.of(4, 5))).isFalse();
    }

    @Test
    void Knight_움직일수있는_경우() {
        assertThat(Chess.KNIGHT.canMove(Point.of(4, 4), Point.of(6, 5))).isTrue();
        assertThat(Chess.KNIGHT.canMove(Point.of(4, 4), Point.of(5, 6))).isTrue();
    }
}
