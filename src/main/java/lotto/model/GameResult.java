package lotto.model;

import java.util.HashMap;
import java.util.Map;

public class GameResult {
    private final Map<Rank, Integer> gameResult;

    public GameResult() {
        this.gameResult = new HashMap<>();
        initialize();
    }

    private void initialize() {
        for (Rank rank : Rank.values()) {
            gameResult.put(rank, 0);
        }
    }

    public void add(Rank rank) {
        gameResult.put(rank, gameResult.get(rank) + 1);
    }

    public int get(Rank rank) {
        return gameResult.get(rank);
    }

    public int getTotalPrizeMoney() {
        int sum = 0;
        for (Rank rank : Rank.values()) {
            sum += rank.getPrizeMoney(gameResult.get(rank));
        }
        return sum;
    }
}
