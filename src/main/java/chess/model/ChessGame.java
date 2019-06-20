package chess.model;

import java.util.Map;

public class ChessGame {
    private final Map<Boolean, User> users;
    private boolean turn = true; // white turn is true.

    public ChessGame(Map<Boolean, User> users) {
        this.users = users;
    }

    public boolean move(Point source, Point destination) {
        User mine = users.get(turn);
        User other = users.get(!turn);

        mine.checkValidPoint(source, destination);
        ChessEnum sourceChess = mine.getChess(source);
        if (!sourceChess.canMove(source, destination, users)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
        if (!ChessEnum.KNIGHT.equals(sourceChess)) {
            checkObstacle(source, destination);
        }
        if (other.isContain(destination)) {
            if (other.isKingAt(destination)) {
                return true;
            }
            other.removePoint(destination);
        }
        mine.replacePoint(source, destination);

        turn = !turn;
        return false;
    }

    private void checkObstacle(Point source, Point destination) {
        int xDirection = getDirection(source.xDistanceFrom(destination));
        int yDirection = getDirection(source.yDistanceFrom(destination));
        Point current = source.next(xDirection, yDirection);

        while (!current.equals(destination)) {
            if (users.get(true).isContain(current) || users.get(false).isContain(current)) {
                throw new IllegalArgumentException("경로상에 장애물이 존재합니다.");
            }
            current = current.next(xDirection, yDirection);
        }
    }

    private int getDirection(double distance) {
        if (distance > 0) {
            return 1;
        }
        if (distance < 0) {
            return -1;
        }
        return 0;
    }
}
