package chess;

import chess.model.board.BoardService;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

import static chess.view.Render.render;

public class ChessGameController {
    public static Route start = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        BoardService.getInstance().initialize();
        model.put("team", BoardService.getInstance().currentTeam());

        Gson gson = new Gson();
        String json = gson.toJson(BoardService.getInstance().getChesses());
        model.put("chesses", json);

        return render(model, "main.html");
    };

    public static Route load = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();

        model.put("team", BoardService.getInstance().currentTeam());
        Gson gson = new Gson();
        String json = gson.toJson(BoardService.getInstance().getChesses());

        model.put("chesses", json);
        model.put("error", request.queryParams("error"));
        return render(model, "main.html");
    };

    public static Route move = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();

        String source = request.queryParams("source");
        String destination = request.queryParams("destination");
        boolean isGameFinish = BoardService.getInstance().move(source, destination);

        Gson gson = new Gson();
        String json = gson.toJson(BoardService.getInstance().getChesses());
        model.put("chesses", json);

        model.put("team", BoardService.getInstance().currentTeam());
        model.put("isGameFinish", isGameFinish);

        return render(model, "main.html");
    };

    public static Route score = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();

        Gson gson = new Gson();
        String json = gson.toJson(BoardService.getInstance().calculateScore());
        model.put("score", json);

        json = gson.toJson(BoardService.getInstance().getChesses());
        model.put("chesses", json);

        model.put("team", BoardService.getInstance().currentTeam());

        return render(model, "main.html");
    };
}
