package chess.model;

import chess.db.BoardDto;
import chess.model.board.Board;
import chess.model.board.ChessInitializer;
import chess.model.piece.King;
import chess.model.piece.Piece;
import chess.model.piece.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BoardTest {
    Board board;

    @BeforeEach
    void setUp() {
        board = new Board(new ChessInitializer(), PlayerType.WHITE);
    }

    @Test
    void 출발지가_자신의_말이_아닌_경우() {
        assertThrows(IllegalArgumentException.class, () -> {
            board.executeMovement(Point.of(1, 7), Point.of(1, 6));
        });
    }

    @Test
    void 도착지가_자신의_말인_경우() {
        assertThrows(IllegalArgumentException.class, () -> {
            board.executeMovement(Point.of(2, 1), Point.of(4, 2));
        });
    }

    @Test
    void 이동경로상에_장애물이_존재하는_경우() {
        assertThrows(IllegalArgumentException.class, () -> {
            board.executeMovement(Point.of(1, 1), Point.of(1, 3));
        });
    }

    @Test
    void 도착지가_King인_경우_true() {
        Board board = new Board(() -> {
            Map<Point, Piece> chessBoard = new HashMap<>();
            chessBoard.put(Point.of(4, 8), new King(PlayerType.BLACK, Point.of(4, 8)));
            chessBoard.put(Point.of(4, 3), new Rook(PlayerType.WHITE, Point.of(4, 3)));
            return chessBoard;
        }, PlayerType.WHITE);

        assertThat(board.executeMovement(Point.of(4, 3), Point.of(4, 8))).isTrue();
    }

    @Test
    void 점수_계산_확인() {
        assertThat(board.calculateScore(PlayerType.WHITE)).isEqualTo(38.0);
    }

    @Test
    void Dto리스트_변환_확인() {
        List<BoardDto> boardDtos = board.convertToDtos(123);

        assertThat(boardDtos.size()).isEqualTo(32);
        assertThat(boardDtos.get(0)).isInstanceOf(BoardDto.class);
    }
}
