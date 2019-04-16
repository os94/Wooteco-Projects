import domain.Movie;
import domain.MovieRepository;
import view.InputView;
import view.OutputView;

import java.util.List;

public class MovieApplication {
    public static void main(String[] args) {
        List<Movie> movies = MovieRepository.getMovies();

        do {
            OutputView.printMovies(movies);

            int movieId = InputView.inputMovieId();
            if (!MovieRepository.hasMovieId(movieId)) {
                continue;
            }
            int index = MovieRepository.getIndexById(movieId);
            Movie movie = movies.get(index);
            OutputView.printMovie(movie);

            int scheduleId = InputView.inputScheduleId();
            if (!movie.isValidScheduleId(scheduleId)) {
                continue;
            }
            // add OneHourWithinRange function.

            

            // temp code
            break;
        } while (true);

    }
}
