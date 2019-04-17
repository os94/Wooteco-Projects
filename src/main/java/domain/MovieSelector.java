/*
 * @class       MovieSelector class
 * @version     1.1.0
 * @date        19.04.17
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       select movie from 'movies', put it in 'movieBag'.
 */

package domain;

import view.InputView;
import view.OutputView;

import java.util.List;

public class MovieSelector {
    private static final String ERROR_NOT_VALID_MOVIE_ID = "유효한 영화 ID가 아닙니다. 상영목록의 영화를 선택하세요.";
    private static final String ERROR_NOT_VALID_SCHEDULE_ID = "유효한 시간표 번호가 아닙니다.";
    private static final String ERROR_NOT_VALID_TIME_OR_PEOPLE = "이미 상영이 시작되었거나, 예매가능인원을 초과하였습니다.";

    private List<Movie> movies;
    private List<SelectedMovie> movieBag;

    public MovieSelector(List<Movie> movies, List<SelectedMovie> movieBag) {
        this.movies = movies;
        this.movieBag = movieBag;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public List<SelectedMovie> getMovieBag() {
        return movieBag;
    }

    public void run() {
        OutputView.printMovies(movies);
        int movieId = getMovieId();
        int movieIndex = MovieRepository.getIndexById(movieId);
        Movie movie = movies.get(movieIndex);
        OutputView.printMovie(movie);
        int scheduleId = getScheduleId(movie);
        int personNo = getPersonNo(movie, scheduleId);

        movieBag.add(new SelectedMovie(movieIndex, scheduleId, personNo));
        movie.updateSchedule(scheduleId, personNo);
    }

    private int getMovieId() {
        int movieId = InputView.inputMovieId();
        if (!MovieRepository.hasMovieId(movieId)) {
            OutputView.printMessage(ERROR_NOT_VALID_MOVIE_ID);
            return getMovieId();
        }
        return movieId;
    }

    private int getScheduleId(Movie movie) {
        int scheduleId = InputView.inputScheduleId();
        if (!movie.isValidScheduleId(scheduleId)) {
            OutputView.printMessage(ERROR_NOT_VALID_SCHEDULE_ID);
            return getScheduleId(movie);
        }
        // add Validate OneHourWithinRange function.
        return scheduleId;
    }

    private int getPersonNo(Movie movie, int scheduleId) {
        int personNo = InputView.inputPersonNo();
        if (!movie.isValidPersonNo(scheduleId, personNo)) {
            OutputView.printMessage(ERROR_NOT_VALID_TIME_OR_PEOPLE);
            return getPersonNo(movie, scheduleId);
        }
        return personNo;
    }
}
