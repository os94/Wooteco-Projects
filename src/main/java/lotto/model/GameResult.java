package lotto.model;

import java.util.HashMap;
import java.util.Map;

public class GameResult {
    private final Map<Rank, Integer> gameResult;

    public GameResult() {
        this.gameResult = new HashMap<>();
    }

    public void add(Rank rank) {
        gameResult.put(rank, gameResult.getOrDefault(rank, 0) + 1);
    }

    public int get(Rank rank) {
        return gameResult.getOrDefault(rank, 0);
    }

    public int getTotalPrizeMoney() {
        int sum = 0;
        for (Rank rank : Rank.values()) {
            sum += rank.getPrizeMoney(gameResult.getOrDefault(rank, 0));
        }
        return sum;
    }
}
