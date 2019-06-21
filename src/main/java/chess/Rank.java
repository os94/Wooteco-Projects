package chess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pieces.Bishop;
import pieces.Blank;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Piece.Color;
import pieces.Piece.Type;
import pieces.Position;
import pieces.Queen;
import pieces.Rook;

public class Rank {
    private List<Piece> pieces = new ArrayList<>(8);
    
    void addPiece(Piece piece) {
        pieces.add(piece);
    }
    
    public List<Piece> getPieces() {
        return Collections.unmodifiableList(pieces);
    }

    int countPieceByColorAndType(Color color, Type type) {
        int countPiece = 0;
        for (Piece piece : pieces) {
            if (piece.matchColorAndType(color, type)) {
                countPiece++;
            }
        }
        return countPiece;
    }

    static Rank initializeWhitePieces(int index) {
        Rank rank = new Rank();
        rank.addPiece(Rook.createWhite(new Position(0, index)));
        rank.addPiece(Knight.createWhite(new Position(1, index)));
        rank.addPiece(Bishop.createWhite(new Position(2, index)));
        rank.addPiece(Queen.createWhite(new Position(3, index)));
        rank.addPiece(King.createWhite(new Position(4, index)));
        rank.addPiece(Bishop.createWhite(new Position(5, index)));
        rank.addPiece(Knight.createWhite(new Position(6, index)));
        rank.addPiece(Rook.createWhite(new Position(7, index)));
        return rank;
    }
    
    static Rank initializeBlackPieces(int index) {
        Rank rank = new Rank();
        rank.addPiece(Rook.createBlack(new Position(0, index)));
        rank.addPiece(Knight.createBlack(new Position(1, index)));
        rank.addPiece(Bishop.createBlack(new Position(2, index)));
        rank.addPiece(Queen.createBlack(new Position(3, index)));
        rank.addPiece(King.createBlack(new Position(4, index)));
        rank.addPiece(Bishop.createBlack(new Position(5, index)));
        rank.addPiece(Knight.createBlack(new Position(6, index)));
        rank.addPiece(Rook.createBlack(new Position(7, index)));
        return rank;
    }
    
    static Rank initializeWhitePawns(int index) {
        Rank rank = new Rank();
        for(int i = 0; i < 8; i++) {
            rank.addPiece(Pawn.createWhite(new Position(i, index)));
        }
        return rank;
    }
    
    static Rank initializeBlackPawns(int index) {
        Rank rank = new Rank();
        for(int i = 0; i < 8; i++) {
            rank.addPiece(Pawn.createBlack(new Position(i, index)));
        }
        return rank;
    }
    
    static Rank initializeBlankLine(int index) {
        Rank rank = new Rank();
        for(int i = 0; i < 8; i++) {
            rank.addPiece(Blank.create(new Position(i, index)));
        }
        return rank;
    }

    Piece findPiece(int xPosition) {
        return pieces.get(xPosition);
    }

    void move(int xPos, Piece piece) {
        pieces.set(xPos, piece);
    }

    List<Piece> findPiecesByColor(Color color) {
        List<Piece> piecesByColor = new ArrayList<>();
        for (Piece piece : pieces) {
            piece.addPiecesByColor(color, piecesByColor);
        }
        return Collections.unmodifiableList(piecesByColor);
    }
}
