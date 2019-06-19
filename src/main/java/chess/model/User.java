package chess.model;

import java.util.Map;

public class User {
    private final Map<Point, Chess> chesses;

    public User(Map<Point, Chess> chesses) {
        this.chesses = chesses;
    }

    public void checkValidPoint(Point source, Point destination) {
        if (!isContain(source)) {
            throw new IllegalArgumentException("사용자의 말을 이동해 주세요.");
        }
        if (isContain(destination)) {
            throw new IllegalArgumentException("사용자의 말이 있는 위치로 이동할 수 없습니다.");
        }
    }

    public boolean isContain(Point point) {
        return chesses.containsKey(point);
    }

    public boolean isKingAt(Point arrival) {
        return chesses.get(arrival) == Chess.KING;
    }

    public void removePoint(Point point) {
        chesses.remove(point);
    }

    public void replacePoint(Point source, Point destination) {
        chesses.put(destination, chesses.get(source));
        removePoint(source);
    }

    public Chess getChess(Point point) {
        return chesses.get(point);
    }
}
