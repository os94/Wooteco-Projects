import domain.Movie;
import domain.MovieRepository;
import domain.SelectedMovie;
import view.InputView;
import view.OutputView;

import java.util.ArrayList;
import java.util.List;

public class MovieApplication {
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


            // temp code
            break;
        } while (true);

    }
}
