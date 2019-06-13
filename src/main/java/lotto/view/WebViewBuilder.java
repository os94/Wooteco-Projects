package lotto.view;

import lotto.model.GameResult;
import lotto.model.Lottos;
import lotto.model.Rank;

import java.util.stream.Collectors;

public class WebViewBuilder {
    public static String of(Lottos lottos) {
        return lottos.getLottos().stream()
                .map(lotto -> lotto + "<br>")
                .collect(Collectors.joining());
    }

    public static String of(GameResult gameResult) {
        StringBuilder stringBuilder = new StringBuilder();

        Rank[] rankValues = Rank.values();
        for (int i = rankValues.length - 2; i >= 0; i--) {
            if (Rank.SECOND == rankValues[i]) {
                stringBuilder.append(rankValues[i].getCountOfMatch()).append("개 일치, 보너스볼 일치, ")
                        .append(rankValues[i].getPrizeMoney()).append("원 : ").append(gameResult.get(rankValues[i])).append("개 <br>");
                continue;
            }
            stringBuilder.append(rankValues[i].getCountOfMatch()).append("개 일치, ")
                    .append(rankValues[i].getPrizeMoney()).append("원 : ").append(gameResult.get(rankValues[i])).append("개 <br>");
        }

        return stringBuilder.toString();
    }
}
