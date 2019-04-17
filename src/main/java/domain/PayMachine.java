/*
 * @class       PayMachine class
 * @version     1.1.0
 * @date        19.04.17
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       pay machine to reserve movies.
 */

package domain;

import view.InputView;
import view.OutputView;

import java.util.List;

public class PayMachine {
    private static final String MESSAGE_START_PAYMENT = "## 결제를 진행합니다.";
    private static final String ERROR_CARD_OR_CASH = "Error: 신용카드는 1번, 현금은 2번을 입력해야합니다.";
    private static final String ERROR_NEGATIVE_NUMBER = "Error: 양의 값을 입력해야합니다.";
    private static final int CODE_CARD = 1;
    private static final int CODE_CASH = 2;
    private static final double DISCOUNT_RATE_CARD = 0.95;
    private static final double DISCOUNT_RATE_CASH = 0.98;

    List<SelectedMovie> movieBag;

    public PayMachine(List<SelectedMovie> movieBag) {
        this.movieBag = movieBag;
    }

    public void pay() {
        OutputView.printMessage(MESSAGE_START_PAYMENT);
        int point = InputView.inputPoint();
        validatePoint(point);

        int cardOrCash = InputView.inputCardOrCash();
        validateCardOrCash(cardOrCash);

        int totalPrice = calculatePrice(movieBag, point, cardOrCash);
        OutputView.printTotalPrice(totalPrice);
    }

    private static void validatePoint(int point) {
        if (point < 0) {
            OutputView.printMessage(ERROR_NEGATIVE_NUMBER);
            System.exit(-1);
        }
    }

    private static void validateCardOrCash(int cardOrCash) {
        if (cardOrCash == CODE_CARD || cardOrCash == CODE_CASH) {
            return;
        }
        OutputView.printMessage(ERROR_CARD_OR_CASH);
        System.exit(-1);
    }

    private static int calculatePrice(List<SelectedMovie> movieBag, int point, int cardOrCash) {
        int sum = 0;
        for (SelectedMovie selectedMovie : movieBag) {
            sum += selectedMovie.getPrice();
        }
        sum -= point;
        return discount(sum, cardOrCash);
    }

    private static int discount(int sum, int cardOrCash) {
        if (cardOrCash == CODE_CARD) {
            return (int) (sum * DISCOUNT_RATE_CARD);
        }
        return (int) (sum * DISCOUNT_RATE_CASH);
    }
}
