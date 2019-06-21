package chess.model;

public enum PlayerType {
    BLACK,
    WHITE,
    NONE;

    public PlayerType toggle() {
        if (this.equals(BLACK)) {
            return WHITE;
        }
        return BLACK;
    }
}
