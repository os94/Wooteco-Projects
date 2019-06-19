package chess.model;

import java.util.HashMap;
import java.util.Map;

public class ChessGame {
    private final Map<Boolean, User> users = new HashMap<>();
    private boolean turn = true; // white turn is true.

    public ChessGame() {
        users.put(true, UserFactory.createWhite());
        users.put(false, UserFactory.createBlack());
    }

    public boolean move(Point source, Point destination) {
        User mine = users.get(turn);
        User other = users.get(!turn);

        mine.checkValidPoint(source, destination);
        Chess sourceChess = mine.getChess(source);
        if (!sourceChess.canMove(source, destination)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
        // Todo 목적지까지 경로 비어있는지 확인
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
}
