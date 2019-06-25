package chess.model;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class BoardServiceTest {
    @Test
    void 초기화시_round를_증가시키고_체스말을_재배치() {
        BoardService boardService = new BoardService();
        try {
            boardService.initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
