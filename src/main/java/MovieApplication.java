/*
 * @class       MovieApplication class
 * @version     1.1.0
 * @date        19.04.17
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       main class for movie reservation.
 */

import domain.*;
import view.InputView;
import view.OutputView;

import java.util.ArrayList;
import java.util.List;

public class MovieApplication {
    private static final String MESSAGE_RESERVATION_FINISH = "예매를 완료했습니다. 즐거운 영화 관람되세요.";

    public static void main(String[] args) {
        List<Movie> movies = MovieRepository.getMovies();
        List<SelectedMovie> movieBag = new ArrayList<>();
        MovieSelector movieSelector;
        PayMachine payMachine;

        while (true) {
            movieSelector = new MovieSelector(movies, movieBag);
            movieSelector.run();

            movies = movieSelector.getMovies();
            movieBag = movieSelector.getMovieBag();

            if (InputView.continueOrExit()) {
                continue;
            }
            OutputView.printSelectedMovies(movies, movieBag);
            break;
        }

        payMachine = new PayMachine(movieBag);
        payMachine.pay();

        OutputView.printMessage(MESSAGE_RESERVATION_FINISH);
    }
}
