package lotto;

import lotto.model.*;
import lotto.model.dao.RoundDAO;
import lotto.model.lottogenerator.LottoFactory;
import lotto.view.WebViewBuilder;
import spark.ModelAndView;
import spark.Request;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class WebUILottoApplication {
    public static void main(String[] args) {
        staticFiles.location("/templates");

        try {
            get("/", (req, res) -> {
                Map<String, Object> model = new HashMap<>();
                model.put("round", currentRound());
                return render(model, "main.html");
            });

            post("/result", (req, res) -> {
                Map<String, Object> model = new HashMap<>();

                playLotto(req, model);

                return render(model, "result.html");
            });
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static int currentRound() throws SQLException {
        return new RoundDAO().getRoundNo();
    }

    private static void playLotto(Request req, Map<String, Object> model) {
        Money money = new Money(Integer.parseInt(req.queryParams("input_money")));
        PositiveNumber countOfManualLotto = new PositiveNumber(Integer.parseInt(req.queryParams("count_of_manual_lotto")));
        PositiveNumber countOfAutoLotto = money.countOfLotto().subtract(countOfManualLotto);
        Lottos lottos = LottoGame.buy(Arrays.asList(req.queryParamsValues("manual_lotto")), countOfAutoLotto);

        model.put("lottos", WebViewBuilder.of(lottos));
        model.put("countOfManualLotto", countOfManualLotto.toString());
        model.put("countOfAutoLotto", countOfAutoLotto.toString());

        WinningLotto winningLotto = new WinningLotto(
                LottoFactory.createManualGenerator(req.queryParams("winning_lotto")),
                LottoNumber.of(Integer.parseInt(req.queryParams("bonus_no"))));
        GameResult gameResult = LottoGame.match(lottos, winningLotto);

        model.put("gameResult", WebViewBuilder.of(gameResult));
        model.put("rateOfProfit", money.rateOfProfit(gameResult.totalPrizeMoney()));
    }

    private static String render(Map<String, Object> model, String templatePath) {
        return new HandlebarsTemplateEngine().render(new ModelAndView(model, templatePath));
    }
}
