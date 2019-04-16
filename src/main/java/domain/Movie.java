package domain;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private static final char NEW_LINE = '\n';

    private final int id;
    private final String name;
    private final int price;

    private List<PlaySchedule> playSchedules = new ArrayList<>();

    public Movie(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    void addPlaySchedule(PlaySchedule playSchedule) {
        playSchedules.add(playSchedule);
    }

    public boolean isMovieId(int movieId) {
        return id == movieId;
    }

    public boolean isValidScheduleId(int scheduleId) {
        return (0 < scheduleId) && (scheduleId <= playSchedules.size());
    }

    public boolean isValidPersonNo(int scheduleId, int personNo) {
        PlaySchedule schedule = playSchedules.get(scheduleId - 1);
        return schedule.isValidCapacity(personNo) && schedule.isValidTime();
    }

    public void updateSchedule(int scheduleId, int personNo) {
        playSchedules.get(scheduleId - 1).updateCapacity(personNo);
    }

    public int getPrice(int personNo) {
        return price * personNo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (PlaySchedule playSchedule : playSchedules) {
            sb.append(playSchedule);
        }
        return id + " - " + name + ", " + price + "원" + NEW_LINE
                + sb.toString();
    }

    public String toString(int scheduleId) {
        StringBuilder sb = new StringBuilder();

        sb.append(id + " - " + name + ", " + price + "원" + NEW_LINE);
        sb.append(playSchedules.get(scheduleId - 1).toString(scheduleId));

        return sb.toString();
    }
}
