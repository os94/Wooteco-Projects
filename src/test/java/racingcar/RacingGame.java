package racingcar;

public class RacingGame {
    public void run() {
        Car car = new Car();
        car.move(new RandomMoveStrategy());
    }
}
