package lotto;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class WebUILottoApplication {
    public static void main(String[] args) {
        LottoGameService lottoGameService = new LottoGameService();

        try {
            staticFiles.location("/templates");

            get("/", (req, res) -> {
                Map<String, Object> model = new HashMap<>();
                model.put("round", lottoGameService.recentRound() + 1);
                return render(model, "main.html");
            });

            post("/result", (req, res) -> {
                Map<String, Object> model;
                model = lottoGameService.result(req.queryParams("input_round"));
                return render(model, "result.html");
            });

            post("/buy-lotto", (req, res) -> {
                Map<String, Object> model = new HashMap<>();
                lottoGameService.buyLotto(req.queryParams("input_money"), req.queryParamsValues("input_manual_lotto"));
                model.put("round", lottoGameService.recentRound());
                return render(model, "winningLotto.html");
            });

            post("/register-winning-lotto", (req, res) -> {
                Map<String, Object> model;
                lottoGameService.registerWinningLotto(req.queryParams("input_winning_lotto"), req.queryParams("input_bonus"));
                int round = lottoGameService.recentRound();
                model = lottoGameService.result(String.valueOf(round));
                return render(model, "result.html");
            });

            get("/error", (req, res) -> {
                Map<String, Object> model = new HashMap<>();
                model.put("message", req.queryParams("message"));
                return render(model, "error.html");
            });

            exception(Exception.class, (exception, req, res) -> {
                res.redirect("/error?message=" + exception.getMessage());
            });
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static String render(Map<String, Object> model, String templatePath) {
        return new HandlebarsTemplateEngine().render(new ModelAndView(model, templatePath));
    }
}
