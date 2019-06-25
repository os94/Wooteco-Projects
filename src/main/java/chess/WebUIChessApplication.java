package chess;

import chess.model.ChessGameController;

import static spark.Spark.get;
import static spark.Spark.staticFiles;

public class WebUIChessApplication {
    public static void main(String[] args) {
        staticFiles.location("/static");

        get("/", ChessGameController.load);

        get("/start", ChessGameController.start);

        get("/move", ChessGameController.move);
    }
}
