package lotto.model;

import java.util.Arrays;

public enum Rank {
    FIRST(6, 2_000_000_000),
    SECOND(5, 30_000_000),
    THIRD(5, 1_500_000),
    FOURTH(4, 50_000),
    FIFTH(3, 5_000),
    MISS(0, 0);

    private int countOfMatch;
    private int prizeMoney;

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
        if (THIRD.matchCount(countOfMatch) && !matchBonus) {
            return THIRD;
        }
        return Arrays.stream(values())
                .filter(rank -> rank.matchCount(countOfMatch))
                .findFirst()
                .orElse(MISS)
                ;
    }

    private boolean matchCount(int countOfMatch) {
        return this.countOfMatch == countOfMatch;
    }
}
