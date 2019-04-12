/*
 * @class       LottoResult class
 * @version     1.0.0
 * @date        19.04.12
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       include Lotto result for each Rank.
 */

package domain;

import java.util.HashMap;
import java.util.Map;

public class LottoResult {
    private Map<Rank, Integer> result = new HashMap<>();

    public LottoResult() {
        for (Rank rank : Rank.values()) {
            result.put(rank, 0);
        }
    }

    public int getNumber(Rank rank) {
        return result.get(rank);
    }

    public void putRank(Rank rank) {
        result.put(rank, result.get(rank) + 1);
    }
}
