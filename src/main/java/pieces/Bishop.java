package pieces;

public class Bishop extends Piece {
    Bishop(Color color, Position position) {
        super(color, Type.BISHOP, position);
    }
    
    public static Bishop createWhite(Position position) {
        return new Bishop(Color.WHITE, position);
    }
    
    public static Bishop createBlack(Position position) {
        return new Bishop(Color.BLACK, position);
    }

    @Override
    protected String getWhiteSymbol() {
        return "&#9815;";
    }

    @Override
    protected String getBlackSymbol() {
        return "&#9821;";
    }

    @Override
    Direction direction(Position source, Position target) {
        return Direction.valueOfDiagonal(source.degree(target));
    }
}
