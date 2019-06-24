package chess.model;

import chess.model.piece.Pawn;
import chess.model.piece.Piece;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class ScoreCalculator {
    List<Piece> pieces;

    public ScoreCalculator(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public double calculateScore(PlayerType team) {
        double sum = pieces.stream()
                .filter(piece -> piece.isSameTeam(team))
                .filter(piece -> !(piece instanceof Pawn))
                .mapToDouble(Piece::getScore)
                .sum();
        return sum + calculatePawnScore(team);
    }

    private double calculatePawnScore(PlayerType team) {
        Map<Integer, Long> xGroup = pieces.stream()
                .filter(piece -> piece.isSameTeam(team))
                .filter(piece -> piece instanceof Pawn)
                .collect(groupingBy(Piece::getX, counting()));

        return xGroup.values().stream()
                .mapToDouble(this::relativePawnScore)
                .sum();
    }

    private double relativePawnScore(long number) {
        if (number >= 2) {
            return number * 0.5;
        }
        return 1;
    }
}
