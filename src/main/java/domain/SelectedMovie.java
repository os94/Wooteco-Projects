/*
 * @class       SelectedMovie class
 * @version     1.0.0
 * @date        19.04.16
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       include data about each selected movie.
 */

package domain;

import utils.DateTimeUtils;

import java.time.LocalDateTime;
import java.util.List;

public class SelectedMovie {
    private static final String RESERVATION_PEOPLE = "예약 인원";

    private final int movieIndex;
    private final int scheduleId;
    private final int personNo;

    public SelectedMovie(int movieIndex, int scheduleId, int personNo) {
        this.movieIndex = movieIndex;
        this.scheduleId = scheduleId;
        this.personNo = personNo;
    }

    public int getPrice() {
        Movie movie = MovieRepository.getMovies().get(movieIndex);
        return movie.getPrice(personNo);
    }

    public String toString(List<Movie> movies) {
        Movie movie = movies.get(movieIndex);
        StringBuilder sb = new StringBuilder();

        sb.append(movie.toString(scheduleId));
        sb.append(RESERVATION_PEOPLE + ": " + personNo + "명");

        return sb.toString();
    }

    public boolean hasGapWith(LocalDateTime startTime) {
        Movie movie = MovieRepository.getMovies().get(movieIndex);
        LocalDateTime selectedMovieTime = movie.getStartDateTime(scheduleId);
        if (DateTimeUtils.isOneHourWithinRange(selectedMovieTime, startTime)) {
            return false;
        }
        return true;
    }
}
