package ladderGame.domain;

import java.util.Map;

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
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String member : gameResult.keySet()) {
            stringBuilder.append(member + " : " + gameResult.get(member) + "\n");
        }
        return stringBuilder.toString();
    }
}
