package chess.model;

import chess.DBManager;
import chess.PointConverter;

import java.sql.SQLException;
import java.util.List;

public class BoardService {
    private BoardDao boardDao;

    public BoardService() {
        this.boardDao = new BoardDao(DBManager.getConnection());
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

    public void move(String inputSource, String inputDestination) throws SQLException {
        Point source = PointConverter.convertToPoint(inputSource);
        Point destination = PointConverter.convertToPoint(inputDestination);

        BoardLoader boardLoader = new BoardLoader();
        // todo : add current board dtos
        Board board = new Board(boardLoader);
        if(board.executeMovement(source,destination)){
            //todo : king die
        }
        //todo
        int round = boardDao.recentRound();
        boardDao.remove(round, destination.toString());
        boardDao.update(round, source.toString(), destination.toString());
    }
}
