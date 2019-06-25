package racingcar;

public class Car {
    private int position = 0;

    public void move(MoveStrategy moveStrategy) {
        if (moveStrategy.movable())
            this.position++;
    }

    public int getPosition() {
        return position;
    }
}
