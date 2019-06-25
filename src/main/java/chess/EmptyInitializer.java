package chess;

import java.util.ArrayList;
import java.util.List;

public class EmptyInitializer implements BoardInitializer {
    @Override
    public List<Rank> initialize() {
        List<Rank> ranks = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            ranks.add(Rank.initializeBlankLine(i));
        }
        return ranks;
    }
}
