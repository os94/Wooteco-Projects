package chess.model;

import java.util.Arrays;
import java.util.List;

public enum Direction {
    NORTH(0, 1),
    NORTHEAST(1, 1),
    EAST(1, 0),
    SOUTHEAST(1, -1),
    SOUTH(0, -1),
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

    private int xDirection;
    private int yDirection;

    Direction(int xDirection, int yDirection) {
        this.xDirection = xDirection;
        this.yDirection = yDirection;
    }

    public static Direction valueOf(int xDistance, int yDistance) {
        if (isTwice(xDistance, yDistance)) {
            int min = Math.min(Math.abs(xDistance), Math.abs(yDistance));
            return Direction.of(xDistance / min, yDistance / min);
        }
        if (xDistance == 0 || yDistance == 0
                || Math.abs(xDistance) == Math.abs(yDistance)) {
            int max = Math.max(Math.abs(xDistance), Math.abs(yDistance));
            return Direction.of(xDistance / max, yDistance / max);
        }
        throw new IllegalArgumentException("유효한 Direction 생성이 아닙니다.");
    }

    private static boolean isTwice(int xDistance, int yDistance) {
        int min = Math.min(Math.abs(xDistance), Math.abs(yDistance));
        int max = Math.max(Math.abs(xDistance), Math.abs(yDistance));
        return max == min * 2;
    }

    private static Direction of(int xDirection, int yDirection) {
        return Arrays.stream(Direction.values())
                .filter(direction -> xDirection == direction.xDirection && yDirection == direction.yDirection)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static List<Direction> rookDirection() {
        return Arrays.asList(NORTH, EAST, SOUTH, WEST);
    }

    public static List<Direction> bishopDirection() {
        return Arrays.asList(NORTHEAST, SOUTHEAST, SOUTHWEST, NORTHWEST);
    }

    public static List<Direction> allDirection() {
        return Arrays.asList(NORTH, EAST, SOUTH, WEST, NORTHEAST, SOUTHEAST, SOUTHWEST, NORTHWEST);
    }

    public static List<Direction> knightDirection() {
        return Arrays.asList(NNE, NNW, SSE, SSW, EEN, EES, WWN, WWS);
    }

    public static List<Direction> pawnDirection(PlayerType currentTeam) {
        if (currentTeam == PlayerType.WHITE) {
            return Arrays.asList(NORTH, NORTHEAST, NORTHWEST);
        }
        return Arrays.asList(SOUTH, SOUTHEAST, SOUTHWEST);
    }

    public int getXDegree() {
        return xDirection;
    }

    public int getYDegree() {
        return yDirection;
    }
}