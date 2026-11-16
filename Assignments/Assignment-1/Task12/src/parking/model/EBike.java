package parking.model;

/** NEW vehicle category: flat fee per visit. */
public class EBike extends Vehicle {
    public static final double FLAT_FEE = 20;
    public EBike(String regNo) { super(regNo); }
    @Override public String typeName() { return "EBIKE"; }
    @Override protected double ratePerHour() { return 0; }          // not used: fee is flat
    @Override public double calculateFee(int minutes) {
        if (minutes < 0) throw new IllegalArgumentException("Duration cannot be negative");
        return minutes == 0 ? 0 : FLAT_FEE;
    }
}
