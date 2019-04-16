package domain;

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
}
