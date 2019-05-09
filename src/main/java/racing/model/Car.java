package racing.model;

public class Car {
    private final static int FORWARD_NUMBER = 4;

    private final String name;
    private int position;

    public Car(String name) {
        this.name = name;
    }

    public Car(String name, int position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void move(int random) {
        if (random >= FORWARD_NUMBER) {
            position++;
        }
    }

    public boolean isMaxPosition(int max) {
        return position == max;
    }
}
