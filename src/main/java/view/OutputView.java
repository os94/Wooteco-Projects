package view;

import domain.Movie;
import domain.SelectedMovie;

import java.util.List;

public class OutputView {
    private static final String RESERVATION_LIST = "예약 내역";
    private static final String START_PAYMENT = "## 결제를 진행합니다.";
    private static final String ERROR_NEGATIVE_NUMBER = "Error: 양의 값을 입력해야합니다.";
    private static final String ERROR_CARD_OR_CASH = "Error: 신용카드는 1번, 현금은 2번을 입력해야합니다.";

    public static void printMovies(List<Movie> movies) {
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }

    public static void printMovie(Movie movie) {
        System.out.println(movie.toString());
    }

    public static void printSelectedMovies(List<Movie> movies, List<SelectedMovie> movieBag) {
        System.out.println(RESERVATION_LIST);
        for (SelectedMovie selectedMovie : movieBag) {
            System.out.println(selectedMovie.toString(movies));
        }
    }

    public static void printStartPayment() {
        System.out.println();
        System.out.println(START_PAYMENT);
    }

    public static void printNegativeNumberError() {
        System.out.println(ERROR_NEGATIVE_NUMBER);
    }

    public static void printCardOrCashError() {
        System.out.println(ERROR_CARD_OR_CASH);
    }
}
