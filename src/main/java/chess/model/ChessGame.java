package chess.model;

import chess.model.chesspiece.Chess;
import chess.model.chesspiece.Knight;

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

        // 내 말인지 확인
        mine.checkValidPoint(source, destination);
        Chess sourceChess = mine.getChess(source);
        // 이동가능 위치인지 확인
        if (!sourceChess.canMove(source, destination)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
        // 중간경로에 장애물 있는지 확인
        if (sourceChess instanceof Knight) {
            checkObstacle(source, destination);
        }
        // pawn확인
        if (sourceChess.isPawn()) {
            if (source.yDistanceFrom(destination) <= 2
                && other.isContain(destination)) {
                throw new IllegalArgumentException("Pawn은 적이 존재하는 위치로 전진할 수 없습니다.");
            }
            if (source.xDistanceFrom(destination) != 0
                && !other.isContain(destination)) {
                throw new IllegalArgumentException("Pawn은 적이 존재하지 않을 경우, 대각선으로 갈 수 없습니다.");
            }
        }
        // 도착지에 적이 있으면 잡기 & 왕이면 종료
        if (other.isContain(destination)) {
            if (other.isKingAt(destination)) {
                return true;
            }
            other.removePoint(destination);
        }
        // 말 위치 변경
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
