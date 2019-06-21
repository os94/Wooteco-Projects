package chess.model;

import chess.model.piece.Pawn;
import chess.model.piece.Piece;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private Map<Point, Piece> chessBoard = new HashMap<>();
    private PlayerType currentTeam = PlayerType.WHITE;

    public void move(Point source, Point destination) {
        //내말 움직이려는지 , 도착지에 내말 있는지
        Piece sourcePiece = chessBoard.get(source);
        Piece destinationPiece = chessBoard.get(destination);

        if (destinationPiece.isSameTeam(currentTeam)) {
            throw new IllegalArgumentException("자신의 말이 도착지에 있으면 안됩니다.");
        } // 도착지에 내 말

        if (!sourcePiece.isSameTeam(currentTeam)) {
            throw new IllegalArgumentException("자신의 말을 움직이세요.");
        }// 출발지에 내 말 아님

        //이동방향이 rule에 없는 방향 확인
        int xDistance = source.xDistanceFrom(destination);
        int yDistance = source.yDistanceFrom(destination);
        Direction direction = Direction.valueOf(xDistance, yDistance);
        if (!sourcePiece.isPossibleDirection(direction, destination)) {
            throw new IllegalArgumentException("움직일 수 있는 방향이 아닙니다.");
        }

        if (!sourcePiece.canMove(direction, destinationPiece)) {
            throw new IllegalArgumentException("움직일 수 없습니다.");
        }

        //중간경로에 장애물이 있는지 확인
        sourcePiece.isPossibleDirection(direction,destination);
        


        currentTeam.toggle();
    }
}
