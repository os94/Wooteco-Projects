package lotto;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.staticFiles;

public class WebUILottoApplication {
    public static void main(String[] args) {
        staticFiles.location("/templates");

        get("/", (req, res) -> {
            Map<String, Object> model;
            model = new LottoGameService().currentRound();
            return render(model, "main.html");
        });
    }

    private static String render(Map<String, Object> model, String templatePath) {
        return new HandlebarsTemplateEngine().render(new ModelAndView(model, templatePath));
    }
}
