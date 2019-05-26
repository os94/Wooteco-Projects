package ladderGame.domain;

import java.util.Map;
import java.util.Objects;

public class GameResult {
    private final Map<String, String> gameResult;

    public GameResult(Map<String, String> gameResult) {
        this.gameResult = gameResult;
    }

    public boolean has(String member) {
        return gameResult.keySet().contains(member);
    }

    public String of(String member) {
        return gameResult.get(member);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameResult that = (GameResult) o;
        return Objects.equals(gameResult, that.gameResult);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameResult);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String member : gameResult.keySet()) {
            stringBuilder.append(member + " : " + gameResult.get(member) + "\n");
        }
        return stringBuilder.toString();
    }
}
