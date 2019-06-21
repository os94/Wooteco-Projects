package pieces;

import java.util.Arrays;
import java.util.List;

import pieces.Position.Degree;

public enum Direction {
	NORTH(0, 1),
	NORTHTWO(0, 2),
	NORTHEAST(1, 1),
	EAST(1, 0),
	SOUTHEAST(1, -1),
	SOUTH(0, -1),
	SOUTHTWO(0, -2),
	SOUTHWEST(-1, -1),
	WEST(-1, 0),
	NORTHWEST(-1, 1),
	
	NNE(1, 2),
	NNW(-1, 2),
	SSE(1, -2),
	SSW(-1, -2),
	EEN(2, 1),
	EES(2, -1),
	WWN(-2, 1),
	WWS(-2, -1);

    private int xDegree;
    private int yDegree;

    private Direction(int xDegree, int yDegree) {
        this.xDegree = xDegree;
        this.yDegree = yDegree;
    }

    public int getXDegree() {
        return xDegree;
    }

    public int getYDegree() {
        return yDegree;
    }
    
    public static Direction valueOf(Degree degree) throws InvalidMovePositionException {
        return valueOf(degree.getXDegree(), degree.getYDegree());
    }
    
    public static Direction valueOfDiagonal(Degree degree) throws InvalidMovePositionException {
        if (!degree.isDiagonal()) {
            throw new InvalidMovePositionException("유효하지 않은 위치입니다.");
        }
        
        return valueOf(degree.getDegreeOne());
    }
    
    public static Direction valueOfLinear(Degree degree) throws InvalidMovePositionException {
        if (!degree.isLinear()) {
            throw new InvalidMovePositionException("유효하지 않은 위치입니다.");
        }
        
        return valueOf(degree.getDegreeOne());
    }
    
    public static Direction valueOfEvery(Degree degree) {
        try {
            return valueOfLinear(degree);
        } catch (InvalidMovePositionException e) {
            return valueOfDiagonal(degree);
        }
    }
    
    public static Direction valueOf(int x, int y) throws InvalidMovePositionException {
        Direction[] directions = values();
        for (Direction direction : directions) {
            if (x == direction.xDegree && y == direction.yDegree) {
                return direction;
            }
        }
        
        throw new InvalidMovePositionException("유효하지 않은 위치입니다.");
    }

    public static List<Direction> linearDirection() {
        return Arrays.asList(NORTH, EAST, SOUTH, WEST);
    }

    public static List<Direction> diagonalDirection() {
        return Arrays.asList(NORTHEAST, SOUTHEAST, SOUTHWEST, NORTHWEST);
    }

    public static List<Direction> everyDirection() {
        return Arrays.asList(NORTH, EAST, SOUTH, WEST, NORTHEAST, SOUTHEAST, SOUTHWEST, NORTHWEST);
    }

    public static List<Direction> knightDirection() {
        return Arrays.asList(NNE, NNW, SSE, SSW, EEN, EES, WWN, WWS);
    }
    
    public static List<Direction> whitePawnDirection() {
        return Arrays.asList(NORTH, NORTHTWO, NORTHEAST, NORTHWEST);
    }
    
    public static List<Direction> blackPawnDirection() {
        return Arrays.asList(SOUTH, SOUTHTWO, SOUTHEAST, SOUTHWEST);
    }
}
