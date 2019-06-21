package pieces;

import java.util.List;

import pieces.Position.Degree;

public class King extends Piece {
    private King(Color color, Position position) {
        super(color, Type.KING, position);
    }
    
    public static King createWhite(Position position) {
        return new King(Color.WHITE, position);
    }
    
    public static King createBlack(Position position) {
        return new King(Color.BLACK, position);
    }
    
    @Override
    public List<Position> verifyMovePosition(Piece target) {
        List<Position> positions = super.verifyMovePosition(target);
        
        Degree degree = degree(target.getPosition());
        if (degree.isOverOneXDegree() || degree.isOverOneYDegree()) {
            throw new InvalidMovePositionException(target + " 위치는 이동할 수 없는 위치입니다.");
        }
        
        return positions;
    }

    @Override
    protected String getWhiteSymbol() {
        return "&#9812;";
    }

    @Override
    protected String getBlackSymbol() {
        return "&#9818;";
    }

    @Override
    Direction direction(Position source, Position target) {
        return Direction.valueOf(source.degree(target));
    }
}
