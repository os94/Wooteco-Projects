package pieces;

public class Knight extends Piece {
    private Knight(Color color, Position position) {
        super(color, Type.KNIGHT, position);
    }
    
    public static Knight createWhite(Position position) {
        return new Knight(Color.WHITE, position);
    }
    
    public static Knight createBlack(Position position) {
        return new Knight(Color.BLACK, position);
    }

    @Override
    protected String getWhiteSymbol() {
        return "&#9816;";
    }

    @Override
    protected String getBlackSymbol() {
        return "&#9822;";
    }

    @Override
    Direction direction(Position source, Position target) {
        return Direction.valueOf(source.degree(target));
    }
}
