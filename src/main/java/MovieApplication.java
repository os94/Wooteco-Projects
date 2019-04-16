import domain.Movie;
import domain.MovieRepository;
import domain.SelectedMovie;
import view.InputView;
import view.OutputView;

import java.util.ArrayList;
import java.util.List;

public class MovieApplication {
    private static final int CODE_CARD = 1;
    private static final int CODE_CASH = 2;

    public static void main(String[] args) {
        List<Movie> movies = MovieRepository.getMovies();
        List<SelectedMovie> movieBag = new ArrayList<>();

        do {
            OutputView.printMovies(movies);

            int movieId = InputView.inputMovieId();
            if (!MovieRepository.hasMovieId(movieId)) {
                continue;
            }
            int movieIndex = MovieRepository.getIndexById(movieId);
            Movie movie = movies.get(movieIndex);
            OutputView.printMovie(movie);

            int scheduleId = InputView.inputScheduleId();
            if (!movie.isValidScheduleId(scheduleId)) {
                continue;
            }
            // add OneHourWithinRange function.

            int personNo = InputView.inputPersonNo();
            if (!movie.isValidPersonNo(scheduleId, personNo)) {
                continue;
            }

            movieBag.add(new SelectedMovie(movieIndex, scheduleId, personNo));
            movie.updateSchedule(scheduleId, personNo);

            if (InputView.continueOrExit()) {
                continue;
            }
            OutputView.printSelectedMovies(movies, movieBag);

            OutputView.printStartPayment();
            int point = InputView.inputPoint();
            validatePoint(point);

            int cardOrCash = InputView.inputCardOrCash();
            validateCardOrCash(cardOrCash);

            // temp code
            break;
        } while (true);

    }

    private static void validatePoint(int point) {
        if (point < 0) {
            OutputView.printNegativeNumberError();
            System.exit(-1);
        }
    }

    private static void validateCardOrCash(int cardOrCash) {
        if (cardOrCash == CODE_CARD || cardOrCash == CODE_CASH) {
            return;
        }
        OutputView.printCardOrCashError();
        System.exit(-1);
    }
}
