package view;

import java.util.Scanner;

public class InputView {
    private static final String GUIDE_INPUT_MOVIE_ID = "## 예약할 영화를 선택하세요.";
    private static final String GUIDE_INPUT_SCHEDULE_ID = "## 예약할 시간표를 선택하세요. (첫번째 상영 시간이 1번)";
    private static final String GUIDE_INPUT_PERSON_NO = "## 예약할 인원을 입력하세요.";
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
            System.out.println(GUIDE_INPUT_PERSON_NO);
            return scanner.nextInt();
        } catch (IllegalArgumentException e) {
            return inputPersonNo();
        }
    }
}
