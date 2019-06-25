package chess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pieces.*;
import pieces.Piece.Color;
import pieces.Piece.Type;
import view.ChessView;

import static org.assertj.core.api.Assertions.*;
import static utils.StringUtils.appendNewLine;

public class BoardTest {
    private Board board;
    private ChessView chessView;

    @BeforeEach
    public void setup() {
        board = new Board();
        chessView = new ChessView();
    }

    @Test
    public void create() throws Exception {
        board.initialize(new RankInitializer());
        String blankRank = appendNewLine("........");
        assertThat(chessView.view(board))
                .isEqualTo(
                    appendNewLine("RNBQKBNR") +
                            appendNewLine("PPPPPPPP") +
                            blankRank +
                            blankRank +
                            blankRank +
                            blankRank +
                            appendNewLine("pppppppp") +
                            appendNewLine("rnbqkbnr")
                );
    }

    @Test
    public void print() throws Exception {
        board.initialize(new RankInitializer());
        System.out.println(chessView.view(board));
    }

    @Test
    public void countPieceByColorAndType() throws Exception {
        board.initialize(new RankInitializer());

        assertThat(board.countPieceByColorAndType(Color.WHITE, Type.PAWN)).isEqualTo(8);
        assertThat(board.countPieceByColorAndType(Color.BLACK, Type.KNIGHT)).isEqualTo(2);
        assertThat(board.countPieceByColorAndType(Color.BLACK, Type.KING)).isEqualTo(1);
    }

    @Test
    public void findPiece() throws Exception {
        board.initialize(new RankInitializer());

        assertFindPiece(Rook.createBlack(new Position("a8")));
        assertFindPiece(Rook.createBlack(new Position("h8")));
        assertFindPiece(Rook.createWhite(new Position("a1")));
        assertFindPiece(Rook.createWhite(new Position("h1")));
    }
    
    private void assertFindPiece(Piece piece) {
        assertThat(board.findPiece(piece.getPosition())).isEqualTo(piece);
    }

    @Test
    public void addPiece() throws Exception {
        board.initialize(new EmptyInitializer());

        Position position = new Position("b5");
        Rook piece = Rook.createBlack(position);
        board.replacePiece(piece);

        assertThat(board.findPiece(position)).isEqualTo(piece);
        System.out.println(chessView.view(board));
    }

    @Test
    public void caculcatePoint() throws Exception {
        board.initialize(new EmptyInitializer());

        addPiece(Pawn.createBlack(new Position("b6")));
        addPiece(Queen.createBlack(new Position("e6")));
        addPiece(King.createBlack(new Position("b8")));
        addPiece(Rook.createBlack(new Position("c8")));

        addPiece(Pawn.createWhite(new Position("f2")));
        addPiece(Pawn.createWhite(new Position("g2")));
        addPiece(Rook.createWhite(new Position("e1")));
        addPiece(King.createBlack(new Position("f1")));

        assertThat(board.caculcatePoint(Color.BLACK)).isCloseTo(15.0, within(0.01));
        assertThat(board.caculcatePoint(Color.WHITE)).isCloseTo(7.0, within(0.01));

        System.out.println(chessView.view(board));
    }

    @Test
    public void caculcatePoint_pawn() throws Exception {
        board.initialize(new EmptyInitializer());

        addPiece(Pawn.createBlack(new Position("b6")));
        addPiece(Pawn.createBlack(new Position("b5")));
        addPiece(Pawn.createBlack(new Position("b4")));

        assertThat(board.caculcatePoint(Color.BLACK)).isCloseTo(1.5, within(0.01));
    }
    
    @Test
    public void move_valid() throws Exception {
        board.initialize(new RankInitializer());
        
        Position sourcePosition = new Position("b2");
        Position targetPosition = new Position("b3");

        board.move(sourcePosition, targetPosition);

        assertThat(board.findPiece(sourcePosition)).isEqualTo(Blank.create(sourcePosition));
        assertThat(board.findPiece(targetPosition)).isEqualTo(Pawn.createWhite(targetPosition));
    }
    
    @Test
    public void move_invalid() throws Exception {
        board.initialize(new RankInitializer());

        assertThatThrownBy(() -> {
            Position sourcePosition = new Position("a1");
            Position targetPosition = new Position("a4");
            board.move(sourcePosition, targetPosition);
        }).isInstanceOf(InvalidMovePositionException.class);
    }
    
    private void addPiece(Piece piece) {
        board.replacePiece(piece);
    }
}
