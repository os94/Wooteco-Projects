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

import static java.util.stream.Collectors.toList;

public class BoardService {
    private static final String TEAM_WHITE = "WHITE";
    private static final String TEAM_BLACK = "BLACK";

    private static BoardService INSTANCE = null;

    private BoardService() {
    }

    public static BoardService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BoardService();
        }
        return INSTANCE;
    }

    public void initialize() throws SQLException {
        Board board = new Board(new ChessInitializer(), PlayerType.WHITE);
        int round = BoardDao.getInstance().recentRound();
        TurnDao.getInstance().addFirstTurn(round + 1);
        BoardDao.getInstance().initialize(makeFields(board.convertToDtos(round + 1)));
    }

    private List<List<Object>> makeFields(List<BoardDto> boardDtos) {
        return boardDtos.stream()
                .map(boardDto -> boardDto.getAll())
                .collect(toList());
    }

    public String currentTeam() throws SQLException {
        int round = BoardDao.getInstance().recentRound();
        return TurnDao.getInstance().selectCurrentTurn(round);
    }

    public List<BoardDto> getChesses() throws SQLException {
        int round = BoardDao.getInstance().recentRound();
        return BoardDao.getInstance().findChesses(round);
    }

    public boolean move(String inputSource, String inputDestination) throws SQLException {
        Point source = PointConverter.convertToPoint(inputSource);
        Point destination = PointConverter.convertToPoint(inputDestination);
        int round = BoardDao.getInstance().recentRound();

        BoardLoader boardLoader = new BoardLoader();
        List<BoardDto> boardDtos = getChesses();
        for (BoardDto boardDto : boardDtos) {
            Point point = PointConverter.convertToPoint(boardDto.getPoint());
            boardLoader.add(point, PieceFactory.create(
                    boardDto.getPiece(), PlayerType.valueOf(boardDto.getTeam()), point));
        }
        Board board = new Board(boardLoader, PlayerType.valueOf(TurnDao.getInstance().selectCurrentTurn(round)));

        if (board.executeMovement(source, destination)) {
            return true;
        }
        BoardDao.getInstance().remove(round, destination.toString());
        BoardDao.getInstance().update(round, source.toString(), destination.toString());

        TurnDao.getInstance().updateCurrentTurn(round);

        return false;
    }

    public Map<String, Double> calculateScore() throws SQLException {
        int round = BoardDao.getInstance().recentRound();

        BoardLoader boardLoader = new BoardLoader();
        List<BoardDto> boardDtos = getChesses();
        for (BoardDto boardDto : boardDtos) {
            Point point = PointConverter.convertToPoint(boardDto.getPoint());
            boardLoader.add(point, PieceFactory.create(
                    boardDto.getPiece(), PlayerType.valueOf(boardDto.getTeam()), point));
        }
        Board board = new Board(boardLoader, PlayerType.valueOf(TurnDao.getInstance().selectCurrentTurn(round)));

        Map<String, Double> scores = new HashMap<>();
        scores.put(TEAM_WHITE, board.calculateScore(PlayerType.WHITE));
        scores.put(TEAM_BLACK, board.calculateScore(PlayerType.BLACK));
        return scores;
    }
}
