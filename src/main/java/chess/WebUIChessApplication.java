package chess;

import chess.model.ChessGameController;

import static spark.Spark.*;

public class WebUIChessApplication {
    public static void main(String[] args) {
        staticFiles.location("/static");

        get("/", ChessGameController.load);

        get("/start", ChessGameController.start);

        post("/move", ChessGameController.move);
    }
}
