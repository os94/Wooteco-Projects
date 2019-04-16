import domain.Movie;
import domain.MovieRepository;
import domain.SelectedMovie;
import view.InputView;
import view.OutputView;

import java.util.ArrayList;
import java.util.List;

public class MovieApplication {
    private static final String ERROR_NOT_VALID_MOVIE_ID = "유효한 영화 ID가 아닙니다. 상영목록의 영화를 선택하세요.";
    private static final String ERROR_NOT_VALID_SCHEDULE_ID = "유효한 시간표 번호가 아닙니다.";
    private static final String ERROR_NOT_VALID_TIME_OR_PEOPLE = "이미 상영이 시작되었거나, 예매가능인원을 초과하였습니다.";
    private static final String ERROR_CARD_OR_CASH = "Error: 신용카드는 1번, 현금은 2번을 입력해야합니다.";
    private static final String ERROR_NEGATIVE_NUMBER = "Error: 양의 값을 입력해야합니다.";
    private static final String MESSAGE_RESERVATION_FINISH = "예매를 완료했습니다. 즐거운 영화 관람되세요.";
    private static final String MESSAGE_START_PAYMENT = "## 결제를 진행합니다.";

    private static final int CODE_CARD = 1;
    private static final int CODE_CASH = 2;
    private static final double DISCOUNT_RATE_CARD = 0.95;
    private static final double DISCOUNT_RATE_CASH = 0.98;

    public static void main(String[] args) {
        List<Movie> movies = MovieRepository.getMovies();
        List<SelectedMovie> movieBag = new ArrayList<>();

        while (true) {
            OutputView.printMovies(movies);

            int movieId = InputView.inputMovieId();
            if (!MovieRepository.hasMovieId(movieId)) {
                OutputView.printMessage(ERROR_NOT_VALID_MOVIE_ID);
                continue;
            }

            int movieIndex = MovieRepository.getIndexById(movieId);
            Movie movie = movies.get(movieIndex);
            OutputView.printMovie(movie);

            int scheduleId = InputView.inputScheduleId();
            if (!movie.isValidScheduleId(scheduleId)) {
                OutputView.printMessage(ERROR_NOT_VALID_SCHEDULE_ID);
                continue;
            }
            // add OneHourWithinRange function.

            int personNo = InputView.inputPersonNo();
            if (!movie.isValidPersonNo(scheduleId, personNo)) {
                OutputView.printMessage(ERROR_NOT_VALID_TIME_OR_PEOPLE);
                continue;
            }

            movieBag.add(new SelectedMovie(movieIndex, scheduleId, personNo));
            movie.updateSchedule(scheduleId, personNo);

            if (InputView.continueOrExit()) {
                continue;
            }
            OutputView.printSelectedMovies(movies, movieBag);

            OutputView.printMessage(MESSAGE_START_PAYMENT);
            int point = InputView.inputPoint();
            validatePoint(point);

            int cardOrCash = InputView.inputCardOrCash();
            validateCardOrCash(cardOrCash);

            int totalPrice = calculatePrice(movieBag, point, cardOrCash);
            OutputView.printTotalPrice(totalPrice);

            OutputView.printMessage(MESSAGE_RESERVATION_FINISH);
            break;
        }
    }

    private static void validatePoint(int point) {
        if (point < 0) {
            OutputView.printMessage(ERROR_NEGATIVE_NUMBER);
            System.exit(-1);
        }
    }

    private static void validateCardOrCash(int cardOrCash) {
        if (cardOrCash == CODE_CARD || cardOrCash == CODE_CASH) {
            return;
        }
        OutputView.printMessage(ERROR_CARD_OR_CASH);
        System.exit(-1);
    }

    private static int calculatePrice(List<SelectedMovie> movieBag, int point, int cardOrCash) {
        int sum = 0;
        for (SelectedMovie selectedMovie : movieBag) {
            sum += selectedMovie.getPrice();
        }
        sum -= point;
        return discount(sum, cardOrCash);
    }

    private static int discount(int sum, int cardOrCash) {
        if (cardOrCash == CODE_CARD) {
            return (int) (sum * DISCOUNT_RATE_CARD);
        }
        return (int) (sum * DISCOUNT_RATE_CASH);
    }
}
