package chess;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static spark.Spark.*;

public class WebUIChessApplication {
    public static void main(String[] args) {
        staticFiles.location("/static");

        get("/", ChessGameController.load);

        get("/start", ChessGameController.start);

        post("/move", ChessGameController.move);

        get("/score", ChessGameController.score);

        exception(Exception.class, (exception, req, res) -> {
            String message = null;
            try {
                message = encodeUTF8(exception.getMessage());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            res.redirect("/?error=" + message);

        });
    }

    private static String encodeUTF8(final String message) throws UnsupportedEncodingException {
        return URLEncoder.encode(message, "UTF-8");
    }
}
