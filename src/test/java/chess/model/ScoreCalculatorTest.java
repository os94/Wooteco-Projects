package chess.model;

import chess.model.piece.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ScoreCalculatorTest {
    @Test
    void 각_Chess말_점수_계산() {
        List<Piece> pieces = Arrays.asList(new Rook(PlayerType.WHITE, Point.of(1, 1))
                , new Knight(PlayerType.WHITE, Point.of(2, 1)), new Bishop(PlayerType.WHITE, Point.of(3, 1))
                , new Queen(PlayerType.WHITE, Point.of(4, 1)), new King(PlayerType.WHITE, Point.of(5, 1))
                , new Bishop(PlayerType.WHITE, Point.of(6, 1)), new Knight(PlayerType.WHITE, Point.of(7, 1))
                , new Rook(PlayerType.WHITE, Point.of(8, 1)));

        assertThat(new ScoreCalculator(pieces).calculateScore(PlayerType.WHITE)).isEqualTo(30);
    }

    @Test
    void Pawn이_세로줄에_한개인_경우() {
        List<Piece> pieces = Arrays.asList(new Pawn(PlayerType.WHITE, Point.of(1, 2)));

        assertThat(new ScoreCalculator(pieces).calculateScore(PlayerType.WHITE)).isEqualTo(1);
    }

    @Test
    void Pawn이_같은_세로줄에_존재하는_경우() {
        List<Piece> pieces = Arrays.asList(new Pawn(PlayerType.WHITE, Point.of(1, 2)),
                new Pawn(PlayerType.WHITE, Point.of(1, 3)), new Pawn(PlayerType.WHITE, Point.of(1, 4)));

        assertThat(new ScoreCalculator(pieces).calculateScore(PlayerType.WHITE)).isEqualTo(1.5);
    }
}
