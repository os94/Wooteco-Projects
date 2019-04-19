/*
 * @class       PlaySchedule class
 * @version     1.0.0
 * @date        19.04.16
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       include each movie schedule.
 */

package domain;

import java.time.LocalDateTime;

import static utils.DateTimeUtils.format;

public class PlaySchedule {
    private static final String START_TIME = "시작시간: ";
    private static final String CAPACITY = " 예약가능인원: ";
    private static final char NEW_LINE = '\n';

    private final LocalDateTime startDateTime;
    private int capacity;

    public PlaySchedule(LocalDateTime startDateTime, int capacity) {
        this.startDateTime = startDateTime;
        this.capacity = capacity;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public boolean isValidCapacity(int personNo) {
        return (0 < personNo) && (personNo <= capacity);
    }

    public boolean isValidTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.isBefore(startDateTime);
    }

    public void updateCapacity(int personNo) {
        capacity -= personNo;
    }

    @Override
    public String toString() {
        return START_TIME + format(startDateTime) + CAPACITY + capacity + NEW_LINE;
    }

    public String toString(int scheduleId) {
        return START_TIME + format(startDateTime) + NEW_LINE;
    }
}
