package chess.model.chesspiece;

import chess.model.Point;

import java.util.Objects;

public abstract class Chess {
    private double score;

    public Chess(double score) {
        this.score = score;
    }

    public abstract boolean canMove(Point source, Point destination);

    public boolean isPawn() {
        return score == 1;
    }

    public boolean isKing() {
        return score == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chess chess = (Chess) o;
        return Double.compare(chess.score, score) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(score);
    }
}
