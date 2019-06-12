package lotto.model;

import lotto.model.dto.GameResultDTO;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public int totalPrizeMoney() {
        int sum = 0;
        for (Rank rank : Rank.values()) {
            sum += rank.getPrizeMoney(gameResult.getOrDefault(rank, 0));
        }
        return sum;
    }

    public GameResultDTO dto() {
        List<Integer> results = Arrays.stream(Rank.values())
                .map(rank -> gameResult.getOrDefault(rank, 0))
                .collect(Collectors.toList());
        return new GameResultDTO(results);
    }
}
