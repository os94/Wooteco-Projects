package lotto.model;

import java.util.HashMap;
import java.util.Map;

public class GameResult {
    Map<Rank, Integer> gameResult;

    public GameResult() {
        this.gameResult = new HashMap<>();
    }

    public void add(Rank rank) {
        gameResult.put(rank, gameResult.get(rank) + 1);
    }
}
