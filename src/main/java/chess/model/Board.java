package chess.model;

import chess.model.piece.Blank;
import chess.model.piece.King;
import chess.model.piece.Piece;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private Map<Point, Piece> chessBoard = new HashMap<>();
    private PlayerType currentTeam = PlayerType.WHITE;

    public boolean move(Point source, Point destination) {
        //내말 움직이려는지 , 도착지에 내말 있는지
        Piece sourcePiece = chessBoard.getOrDefault(source, new Blank(PlayerType.NONE, source));
        Piece destinationPiece = chessBoard.getOrDefault(destination, new Blank(PlayerType.NONE, destination));

        if (destinationPiece.isSameTeam(currentTeam)) {
            throw new IllegalArgumentException("자신의 말이 도착지에 있으면 안됩니다.");
        } // 도착지에 내 말

        if (!sourcePiece.isSameTeam(currentTeam)) {
            throw new IllegalArgumentException("자신의 말을 움직이세요.");
        }// 출발지에 내 말 아님

        //이동방향이 rule에 없는 방향 확인
        Direction direction = source.calculateDirection(destination);
        if (!sourcePiece.canMove(direction, destination)) {
            throw new IllegalArgumentException("이동할 수 있는 위치가 아닙니다.");
        }

        if (!sourcePiece.isAvailableDestinationOfPawn(direction, destinationPiece)) {
            throw new IllegalArgumentException("Pawn이 이동할 수 있는 위치가 아닙니다.");
        }

        checkObstacle(source, destination);

        // 이동로직
        chessBoard.remove(source);
        sourcePiece.move(destination);
        chessBoard.put(destination, sourcePiece);


        if (destinationPiece instanceof King) {
            return true;
        }

        currentTeam.toggle();
        return false;
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
}
