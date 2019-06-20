package chess;

import chess.model.ChessGame;
import chess.model.UserFactory;

public class ConsoleUIChessApplication {
    public static void main(String[] args) {
        ChessGame chessGame = new ChessGame(UserFactory.createUsers());
    }
}
