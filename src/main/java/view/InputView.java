package view;

import java.util.Scanner;

public class InputView {
    private static final String GUIDE_INPUT_MOVIE_ID = "## 예약할 영화를 선택하세요.";
    private static final Scanner scanner = new Scanner(System.in);

    public static int inputMovieId() {
        try {
            System.out.println(GUIDE_INPUT_MOVIE_ID);
            return scanner.nextInt();
        } catch (IllegalArgumentException e) {
            return inputMovieId();
        }
    }
}
