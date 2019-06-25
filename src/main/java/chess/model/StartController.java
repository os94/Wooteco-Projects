package chess.model;

import spark.Request;
import spark.Response;
import spark.Route;

public class StartController {
    public Route start = (Request request, Response response) -> {
        BoardService boardService = new BoardService();
        boardService.initialize();

        return null;
    };
}
