package parking.model;

public class TwoWheeler extends Vehicle {
    public TwoWheeler(String regNo) { super(regNo); }
    @Override public String typeName() { return "TWOWHEELER"; }
    @Override protected double ratePerHour() { return 10; }
}
