package parking.model;

public class Car extends Vehicle {
    public Car(String regNo) { super(regNo); }
    @Override public String typeName() { return "CAR"; }
    @Override protected double ratePerHour() { return 30; }
}
