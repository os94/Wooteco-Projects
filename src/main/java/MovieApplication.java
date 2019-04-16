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
            
            // temp code
            break;
        } while (true);

    }
}
