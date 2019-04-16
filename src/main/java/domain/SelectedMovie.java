package domain;

public class SelectedMovie {
    private final int movieId;
    private final int scheduleId;
    private final int personNo;

    public SelectedMovie(int movieId, int scheduleId, int personNo) {
        this.movieId = movieId;
        this.scheduleId = scheduleId;
        this.personNo = personNo;
    }


}
