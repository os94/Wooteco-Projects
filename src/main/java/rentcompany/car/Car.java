package rentcompany.car;

public abstract class Car {
    private double distance;
    private double distancePerLiter;

    public Car(double distance, double distancePerLiter) {
        this.distance = distance;
        this.distancePerLiter = distancePerLiter;
    }

    public abstract String getName();

    public double getChargeQuantity() {
        return distance / distancePerLiter;
    }
}
