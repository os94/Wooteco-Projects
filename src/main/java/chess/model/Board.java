package chess.model;

import chess.model.piece.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class Board {
    private Map<Point, Piece> chessBoard = new HashMap<>();
    private PlayerType currentTeam = PlayerType.WHITE;

    public Board() {
        initialize();
    }

    private void initialize() {
        createChessPieces(PlayerType.WHITE);
        createPawn(PlayerType.WHITE);
        createChessPieces(PlayerType.BLACK);
        createPawn(PlayerType.BLACK);
    }

    public boolean executeMovement(Point source, Point destination) {
        checkMyPiece(source, destination);
        checkMovablePoint(source, destination);
        checkObstacle(source, destination);
        move(source, destination);
        currentTeam.toggle();
        return chessBoard.get(destination) instanceof King;
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
        return new ScoreCalculator(chessBoard.values()).calculateScore(team);
    }

    private void createChessPieces(PlayerType team) {
        List<BiFunction<PlayerType, Point, Piece>> pieces =
                Arrays.asList(Rook::new, King::new, Bishop::new, King::new,
                        Queen::new, Bishop::new, Knight::new, Rook::new);
        int pieceYPoint = team == PlayerType.WHITE ? 1 : 8;
        for (int i = 1; i <= 8; i++) {
            chessBoard.put(Point.of(i, pieceYPoint), pieces.get(i - 1).apply(team, Point.of(i, pieceYPoint)));
        }
    }

    private void createPawn(PlayerType team) {
        int pawnYPoint = team == PlayerType.WHITE ? 2 : 7;
        for (int i = 1; i <= 8; i++) {
            Point point = Point.of(i, pawnYPoint);
            chessBoard.put(point, new Pawn(team, point));
        }
    }
}
