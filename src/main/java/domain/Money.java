/*
 * @class       Money class
 * @version     1.0.0
 * @date        19.04.11
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       include methods about money.
 */

package domain;

import java.util.List;

public class Money {
    private static final String EXIT_MESSAGE_LACK_OF_MONEY = "금액이 부족합니다. 게임을 종료합니다.";
    private static final int PRICE_OF_LOTTO = 1_000;
    private int inputMoney;

    public Money(int inputMoney) {
        if (inputMoney < PRICE_OF_LOTTO) {
            throw new IllegalArgumentException(EXIT_MESSAGE_LACK_OF_MONEY);
        }
        this.inputMoney = inputMoney;
    }

    public int getMaxNumberOfLotto() {
        return inputMoney / PRICE_OF_LOTTO;
    }

    public double getProfit(LottoResult lottoResult) {
        double sum = 0;

        for (Rank rank : Rank.values()) {
            sum += (double) rank.prize(lottoResult.getNumber(rank));
        }
        return sum / inputMoney;
    }
}
