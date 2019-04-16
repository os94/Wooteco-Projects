/*
 * @class       InputView class
 * @version     1.0.0
 * @date        19.04.16
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       get input from user.
 */

package view;

import java.util.Scanner;

public class InputView {
    private static final String GUIDE_INPUT_MOVIE_ID = "## 예약할 영화를 선택하세요.";
    private static final String GUIDE_INPUT_SCHEDULE_ID = "## 예약할 시간표를 선택하세요. (첫번째 상영 시간이 1번)";
    private static final String GUIDE_INPUT_PERSON_NO = "## 예약할 인원을 입력하세요.";
    private static final String GUIDE_INPUT_CONTINUE_OR_EXIT = "## 예약을 종료하고 결제를 진행하려면 1번, 추가 예약을 진행하려면 2번";
    private static final String GUIDE_INPUT_POINT = "## 포인트 사용 금액을 입력하세요. 포인트가 없으면 0 입력";
    private static final String GUIDE_INPUT_CARD_OR_CASH = "## 신용카드는 1번, 현금은 2번";
    private static final int CODE_CONTINUE = 2;
    private static final int CODE_EXIT = 1;
    private static final char NEW_LINE = '\n';
    private static final Scanner scanner = new Scanner(System.in);

    public static int inputMovieId() {
        try {
            System.out.println(GUIDE_INPUT_MOVIE_ID);
            return scanner.nextInt();
        } catch (IllegalArgumentException e) {
            return inputMovieId();
        }
    }

    public static int inputScheduleId() {
        try {
            System.out.println(GUIDE_INPUT_SCHEDULE_ID);
            return scanner.nextInt();
        } catch (IllegalArgumentException e) {
            return inputScheduleId();
        }
    }

    public static int inputPersonNo() {
        try {
            System.out.println(NEW_LINE + GUIDE_INPUT_PERSON_NO);
            return scanner.nextInt();
        } catch (IllegalArgumentException e) {
            return inputPersonNo();
        }
    }

    public static int inputPoint() {
        try {
            System.out.println(GUIDE_INPUT_POINT);
            return scanner.nextInt();
        } catch (IllegalArgumentException e) {
            return inputPoint();
        }
    }

    public static int inputCardOrCash() {
        try {
            System.out.println(NEW_LINE + GUIDE_INPUT_CARD_OR_CASH);
            return scanner.nextInt();
        } catch (IllegalArgumentException e) {
            return inputCardOrCash();
        }
    }

    public static boolean continueOrExit() throws IllegalArgumentException{
        System.out.println(NEW_LINE + GUIDE_INPUT_CONTINUE_OR_EXIT);
        int input = scanner.nextInt();

        if (input == CODE_CONTINUE) {
            return true;
        }
        if (input == CODE_EXIT) {
            return false;
        }
        return continueOrExit();
    }
}
