package domain;

import utils.DateTimeUtils;

import java.time.LocalDateTime;

import static utils.DateTimeUtils.format;

public class PlaySchedule {
    private final LocalDateTime startDateTime;
    private int capacity;

    public PlaySchedule(LocalDateTime startDateTime, int capacity) {
        this.startDateTime = startDateTime;
        this.capacity = capacity;
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
        return "시작시간: " + format(startDateTime) + " 예약가능인원: " + capacity + "\n";
    }
}
