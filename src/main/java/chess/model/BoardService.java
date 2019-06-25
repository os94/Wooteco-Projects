package chess.model;

import chess.DBManager;

import java.sql.SQLException;
import java.util.List;

public class BoardService {
    private BoardDao boardDao;

    public BoardService() {
        this.boardDao = new BoardDao(DBManager.getConnection());
    }

    public void delete() throws SQLException {
        boardDao.delete();
    }
    
    public void initialize() throws SQLException {
        Board board = new Board(new ChessInitializer());
        int round = boardDao.recentRound();
        boardDao.initialize(board.convertToDto(round + 1));
    }

    public String currentTeam() throws SQLException {
        return boardDao.currentTeam();
    }

    public List<BoardDto> getChesses() throws SQLException {
        int round = boardDao.recentRound();
        return boardDao.findChesses(round);
    }
}
