package view;

import domain.Movie;
import domain.SelectedMovie;

import java.util.List;

public class OutputView {
    private static final String RESERVATION_LIST = "예약 내역";

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
}
