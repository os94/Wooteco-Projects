package chess;

import pieces.Piece.Color;
import pieces.Position;

public class ChessGame {
    private Board board = new Board();
    
    public Board getBoard() {
        return board;
    }

    public void initialize() {
        board.initialize();
    }

    public void move(String source, String target) {
        board.move(new Position(source), new Position(target));
    }
    
    public double getPointByWhite() {
        return board.caculcatePoint(Color.WHITE);
    }
    
    public double getPointByBlack() {
        return board.caculcatePoint(Color.BLACK);
    }
}
