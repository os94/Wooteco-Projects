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
            int index = MovieRepository.getIndexById(movieId);
            Movie movie = movies.get(index);
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

            movieBag.add(new SelectedMovie(movieId, scheduleId, personNo));
            movie.updateSchedule(scheduleId, personNo);


            // temp code
            break;
        } while (true);

    }
}
