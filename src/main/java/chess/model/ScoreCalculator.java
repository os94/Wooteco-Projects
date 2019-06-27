package chess.model;

import chess.model.piece.Piece;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class ScoreCalculator {
    private static final double SCORE_OF_PAWN = 1;
    private static final double HALF_SCORE_OF_PAWN = 0.5;
    private static final int LIMIT_PAWN_NO_OF_ONE_LINE = 2;

    private final List<Piece> pieces;

    public ScoreCalculator(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public double calculateScore(PlayerType team) {
        double sum = pieces.stream()
                .filter(piece -> piece.isSameTeam(team))
                .filter(piece -> !piece.isPawn())
                .mapToDouble(Piece::getScore)
                .sum();
        return sum + calculatePawnScore(team);
    }

    private double calculatePawnScore(PlayerType team) {
        Map<Integer, Long> xGroup = pieces.stream()
                .filter(piece -> piece.isSameTeam(team))
                .filter(Piece::isPawn)
                .collect(groupingBy(Piece::getX, counting()));

        return xGroup.values().stream()
                .mapToDouble(this::relativePawnScore)
                .sum();
    }

    private double relativePawnScore(long number) {
        if (number >= LIMIT_PAWN_NO_OF_ONE_LINE) {
            return number * HALF_SCORE_OF_PAWN;
        }
        return SCORE_OF_PAWN;
    }
}
