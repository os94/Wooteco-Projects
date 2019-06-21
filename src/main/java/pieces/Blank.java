package pieces;

import java.util.List;

public class Blank extends Piece {
    private Blank(Position position) {
        super(Color.NOCOLOR, Type.NO_PIECE, position);
    }
    
    public static Blank create(Position position) {
        return new Blank(position);
    }

    @Override
    public List<Position> verifyMovePosition(Piece target) {
        throw new InvalidMovePositionException("말이 아니라 이동할 수 없습니다.");
    }

    @Override
    protected String getWhiteSymbol() {
        return "";
    }

    @Override
    protected String getBlackSymbol() {
        return "";
    }

    @Override
    Direction direction(Position source, Position target) {
        return null;
    }
}
