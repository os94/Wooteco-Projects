/*
 * @class       Movie class
 * @version     1.0.0
 * @date        19.04.16
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       include id, name, price, schedule ..
 */

package domain;

import view.OutputView;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private static final String ERROR_NOT_VALID_SCHEDULE_ID = "유효한 시간표 번호가 아닙니다.";
    private static final String ERROR_MOVIE_ALREADY_STARTED = "이미 상영이 시작하였습니다.";
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
        if (!isScheduleId(scheduleId)) {
            OutputView.printMessage(ERROR_NOT_VALID_SCHEDULE_ID);
            return false;
        }
        if (!isBeforeStart(scheduleId)) {
            OutputView.printMessage(ERROR_MOVIE_ALREADY_STARTED);
            return false;
        }
        return true;
    }

    public boolean isValidPersonNo(int scheduleId, int personNo) {
        PlaySchedule schedule = playSchedules.get(scheduleId - 1);
        return schedule.isValidCapacity(personNo);
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

    private boolean isScheduleId(int scheduleId) {
        return (0 < scheduleId) && (scheduleId <= playSchedules.size());
    }

    private boolean isBeforeStart(int scheduleId) {
        PlaySchedule schedule = playSchedules.get(scheduleId - 1);
        return schedule.isValidTime();
    }
}
