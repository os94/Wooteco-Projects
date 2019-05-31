package lotto.model;

public enum Rank {
    FIRST(6, 2_000_000_000),
    SECOND(5, 30_000_000),
    THIRD(5, 1_500_000),
    FOURTH(4, 50_000),
    FIFTH(3, 5_000),
    MISS(0, 0);

    private static final int MIN_LIMIT_OF_WIN = 3;
    private static final String ERROR_INVALID_RANK = "는 유효하지 않은 값입니다.";

    private int countOfMatch;
    private int prizeMoney;

    private Rank(int countOfMatch, int prizeMoney) {
        this.countOfMatch = countOfMatch;
        this.prizeMoney = prizeMoney;
    }

    public int getCountOfMatch() {
        return countOfMatch;
    }

    public int getPrizeMoney() {
        return prizeMoney;
    }

    public static Rank valueOf(int countOfMatch, boolean matchBonus) {
        if (countOfMatch < MIN_LIMIT_OF_WIN) {
            return MISS;
        }
        if (THIRD.matchCount(countOfMatch) && !matchBonus) {
            return THIRD;
        }
        for (Rank rank : Rank.values()) {
            if (rank.matchCount(countOfMatch)) {
                return rank;
            }
        }
        throw new IllegalArgumentException(countOfMatch + ERROR_INVALID_RANK);
    }

    private boolean matchCount(int countOfMatch) {
        return this.countOfMatch == countOfMatch;
    }
}
