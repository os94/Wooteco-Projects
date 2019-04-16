package view;

import domain.Movie;
import domain.SelectedMovie;

import java.util.List;

public class OutputView {
    private static final String RESERVATION_LIST = "예약 내역";
    private static final String MESSAGE_TOTAL_PRICE_FRONT = "최종 결제한 금액은 ";
    private static final String MESSAGE_TOTAL_PRICE_BACK = "원 입니다.";
    private static final char NEW_LINE = '\n';

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

    public static void printTotalPrice(int totalPrice) {
        System.out.println(NEW_LINE + MESSAGE_TOTAL_PRICE_FRONT + totalPrice + MESSAGE_TOTAL_PRICE_BACK);
    }

    public static void printMessage(String message) {
        System.out.println(NEW_LINE + message);
    }
}
