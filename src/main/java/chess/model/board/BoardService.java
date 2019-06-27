package chess.model.board;

import chess.db.BoardDao;
import chess.db.BoardDto;
import chess.db.TurnDao;
import chess.model.PlayerType;
import chess.model.Point;
import chess.model.piece.PieceFactory;
import chess.util.PointConverter;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardService {
    private static final String TEAM_WHITE = "WHITE";
    private static final String TEAM_BLACK = "BLACK";

    private final BoardDao boardDao;
    private final TurnDao turnDao;

    public BoardService() {
        this.boardDao = BoardDao.getInstance();
        this.turnDao = TurnDao.getInstance();
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

    public boolean move(String inputSource, String inputDestination) throws SQLException {
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
            return true;
        }
        boardDao.remove(round, destination.toString());
        boardDao.update(round, source.toString(), destination.toString());

        turnDao.updateCurrentTurn(round);

        return false;
    }

    public Map<String, Double> calculateScore() throws SQLException {
        int round = boardDao.recentRound();

        BoardLoader boardLoader = new BoardLoader();
        List<BoardDto> boardDtos = getChesses();
        for (BoardDto boardDto : boardDtos) {
            Point point = PointConverter.convertToPoint(boardDto.getPoint());
            boardLoader.add(point, PieceFactory.create(
                    boardDto.getPiece(), PlayerType.valueOf(boardDto.getTeam()), point));
        }
        Board board = new Board(boardLoader, PlayerType.valueOf(turnDao.selectCurrentTurn(round)));

        Map<String, Double> scores = new HashMap<>();
        scores.put(TEAM_WHITE, board.calculateScore(PlayerType.WHITE));
        scores.put(TEAM_BLACK, board.calculateScore(PlayerType.BLACK));
        return scores;
    }
}
