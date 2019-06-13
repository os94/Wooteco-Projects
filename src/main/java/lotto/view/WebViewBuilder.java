package lotto.view;

import lotto.model.Lottos;

import java.util.stream.Collectors;

public class WebViewBuilder {
    public static String of(Lottos lottos) {
        return lottos.getLottos().stream()
                .map(lotto -> lotto + "<br>")
                .collect(Collectors.joining());
    }
}
