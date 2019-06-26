package chess.model.board;

import chess.db.DBManager;
import chess.db.TurnDao;
import chess.model.PlayerType;
import chess.model.Point;
import chess.util.PointConverter;
import chess.db.BoardDao;
import chess.db.BoardDto;
import chess.model.piece.PieceFactory;

import java.sql.SQLException;
import java.util.List;

public class BoardService {
    private BoardDao boardDao;
    private TurnDao turnDao;

    public BoardService() {
        this.boardDao = new BoardDao(DBManager.getConnection());
        this.turnDao = new TurnDao(DBManager.getConnection());
    }

    public void initialize() throws SQLException {
        Board board = new Board(new ChessInitializer(), PlayerType.WHITE);
        int round = boardDao.recentRound();
        turnDao.addFirstTurn(round + 1);
        boardDao.initialize(board.convertToDto(round + 1));
    }

    public String currentTeam() throws SQLException {
        int round = boardDao.recentRound();
        return turnDao.selectCurrentTurn(round);
    }

    public List<BoardDto> getChesses() throws SQLException {
        int round = boardDao.recentRound();
        return boardDao.findChesses(round);
    }

    public String move(String inputSource, String inputDestination) throws SQLException {
        Point source = PointConverter.convertToPoint(inputSource);
        Point destination = PointConverter.convertToPoint(inputDestination);
        int round = boardDao.recentRound();

        BoardLoader boardLoader = new BoardLoader();
        List<BoardDto> boardDtos = getChesses();
        for (BoardDto boardDto : boardDtos) {
            Point point = PointConverter.convertToPoint(boardDto.getPoint());
            boardLoader.add(point, PieceFactory.create(
                    boardDto.getPiece(), PlayerType.valueOf(boardDto.getTeam()), point));
        }
        Board board = new Board(boardLoader, PlayerType.valueOf(turnDao.selectCurrentTurn(round)));
        
        if (board.executeMovement(source, destination)) {
            //todo : king die
            return turnDao.selectCurrentTurn(round) + "팀 승리!";
        }
        boardDao.remove(round, destination.toString());
        boardDao.update(round, source.toString(), destination.toString());

        turnDao.updateCurrentTurn(round);

        return "게임 진행중...";
    }
}
