package parking.model;

public class ElectricCar extends Car implements Chargeable {
    public ElectricCar(String regNo) { super(regNo); }
    @Override public String typeName() { return "EV"; }
    @Override protected double ratePerHour() { return 35; }
    @Override public double chargingFee(int kWh) {
        if (kWh < 0) throw new IllegalArgumentException("kWh cannot be negative");
        return kWh * 12;
    }
}
