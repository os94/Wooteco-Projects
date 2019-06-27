package chess.model.board;

import chess.db.BoardDto;
import chess.model.Direction;
import chess.model.PlayerType;
import chess.model.Point;
import chess.model.ScoreCalculator;
import chess.model.piece.Blank;
import chess.model.piece.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class Board {
    private final Map<Point, Piece> chessBoard;
    private PlayerType currentTeam;

    public Board(BoardInitializer initializer, PlayerType currentTeam) {
        this.chessBoard = initializer.initialize();
        this.currentTeam = currentTeam;
    }

    public boolean executeMovement(Point source, Point destination) {
        checkMyPiece(source, destination);
        checkMovablePoint(source, destination);
        checkObstacle(source, destination);
        if (chessBoard.getOrDefault(destination, new Blank(PlayerType.NONE, destination)).isKing()) {
            return true;
        }
        move(source, destination);
        currentTeam.toggle();
        return false;
    }

    private void checkMyPiece(Point source, Point destination) {
        Piece sourcePiece = chessBoard.getOrDefault(source, new Blank(PlayerType.NONE, source));
        Piece destinationPiece = chessBoard.getOrDefault(destination, new Blank(PlayerType.NONE, destination));

        if (!sourcePiece.isSameTeam(currentTeam)) {
            throw new IllegalArgumentException("자신의 말을 움직이세요.");
        }
        if (destinationPiece.isSameTeam(currentTeam)) {
            throw new IllegalArgumentException("자신의 말이 도착지에 있으면 안됩니다.");
        }
    }

    private void checkMovablePoint(Point source, Point destination) {
        Piece sourcePiece = chessBoard.getOrDefault(source, new Blank(PlayerType.NONE, source));
        Piece destinationPiece = chessBoard.getOrDefault(destination, new Blank(PlayerType.NONE, destination));
        Direction direction = source.calculateDirection(destination);

        if (!sourcePiece.canMove(direction, destination)) {
            throw new IllegalArgumentException("이동할 수 있는 위치가 아닙니다.");
        }
        if (!sourcePiece.isAvailableDestinationOfPawn(direction, destinationPiece)) {
            throw new IllegalArgumentException("Pawn이 이동할 수 있는 위치가 아닙니다.");
        }
    }

    private void move(Point source, Point destination) {
        Piece sourcePiece = chessBoard.get(source);

        chessBoard.remove(source);
        sourcePiece.move(destination);
        chessBoard.put(destination, sourcePiece);
    }

    private void checkObstacle(Point source, Point destination) {
        Direction direction = source.calculateDirection(destination);

        Point current = source.next(direction);
        while (!current.equals(destination)) {
            if (chessBoard.containsKey(current)) {
                throw new IllegalArgumentException("경로상에 장애물이 존재합니다.");
            }
            current = current.next(direction);
        }
    }

    public double calculateScore(PlayerType team) {
        List<Piece> pieces = new ArrayList<>(chessBoard.values());
        return new ScoreCalculator(pieces).calculateScore(team);
    }

    public List<BoardDto> convertToDtos(int round) {
        List<BoardDto> boardDtos = chessBoard.keySet().stream()
                .map(point -> new BoardDto(
                        chessBoard.get(point).toString(),
                        chessBoard.get(point).getTeam(),
                        point.toString(),
                        round))
                .collect(toList());
        return boardDtos;
    }
}
