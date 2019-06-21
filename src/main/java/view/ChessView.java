package view;

import static utils.StringUtils.appendNewLine;

import java.util.List;
import java.util.ListIterator;

import chess.Board;
import chess.Rank;
import pieces.Piece;

public class ChessView {
    public String view(Board board) {
        StringBuilder sb = new StringBuilder();
        List<Rank> ranks = board.getRanks();
        ListIterator<Rank> rankIter = ranks.listIterator(ranks.size());
        while (rankIter.hasPrevious()) {
            sb.append(showRank(rankIter.previous()));
        }
        return sb.toString();
    }
    
    private String showRank(Rank rank) {
        StringBuilder sb = new StringBuilder();
        for (Piece piece : rank.getPieces()) {
            sb.append(piece.getRepresentation());
        }
        return appendNewLine(sb.toString());
    }
}
