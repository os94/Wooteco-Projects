package racingcar;

import java.util.Random;

public class Car {
    private static final int FORWARD_NUM = 4;
    private static final int MAX_BOUND = 10;

    private int position = 0;

    public void move() {
        if (getRandomNo() >= FORWARD_NUM)
            this.position++;
    }

    private int getRandomNo() {
        Random random = new Random();
        return random.nextInt(MAX_BOUND);
    }

    public int getPosition() {
        return position;
    }
}
