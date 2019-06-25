package chess;

import java.util.ArrayList;
import java.util.List;

public class RankInitializer implements BoardInitializer {
    @Override
    public List<Rank> initialize() {
        List<Rank> ranks = new ArrayList<>();
        ranks.add(Rank.initializeWhitePieces(0));
        ranks.add(Rank.initializeWhitePawns(1));
        ranks.add(Rank.initializeBlankLine(2));
        ranks.add(Rank.initializeBlankLine(3));
        ranks.add(Rank.initializeBlankLine(4));
        ranks.add(Rank.initializeBlankLine(5));
        ranks.add(Rank.initializeBlackPawns(6));
        ranks.add(Rank.initializeBlackPieces(7));
        return ranks;
    }
}
