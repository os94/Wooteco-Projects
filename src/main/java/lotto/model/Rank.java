package lotto.model;

import java.util.HashMap;
import java.util.Map;

public enum Rank {
    FIRST(6, 2_000_000_000),
    SECOND(5, 30_000_000),
    THIRD(5, 1_500_000),
    FOURTH(4, 50_000),
    FIFTH(3, 5_000),
    MISS(0, 0);

    private static final String ERROR_INVALID_RANK = "는 유효하지 않은 값입니다.";
    private static final Map<Integer, Rank> ranks = new HashMap<>();

    private int countOfMatch;
    private int prizeMoney;

    static {
        ranks.put(6, FIRST);
        ranks.put(4, FOURTH);
        ranks.put(3, FIFTH);
        ranks.put(2, MISS);
        ranks.put(1, MISS);
        ranks.put(0, MISS);
    }

    Rank(int countOfMatch, int prizeMoney) {
        this.countOfMatch = countOfMatch;
        this.prizeMoney = prizeMoney;
    }

    public int getCountOfMatch() {
        return countOfMatch;
    }

    public int getPrizeMoney() {
        return prizeMoney;
    }

    public int getPrizeMoney(int number) {
        return prizeMoney * number;
    }

    public static Rank valueOf(int countOfMatch, boolean matchBonus) {
        if (ranks.containsKey(countOfMatch)) {
            return ranks.get(countOfMatch);
        }
        if (SECOND.matchCount(countOfMatch) && matchBonus) {
            return SECOND;
        }
        if (THIRD.matchCount(countOfMatch) && !matchBonus) {
            return THIRD;
        }
        throw new IllegalArgumentException(countOfMatch + ERROR_INVALID_RANK);
    }

    private boolean matchCount(int countOfMatch) {
        return this.countOfMatch == countOfMatch;
    }
}
