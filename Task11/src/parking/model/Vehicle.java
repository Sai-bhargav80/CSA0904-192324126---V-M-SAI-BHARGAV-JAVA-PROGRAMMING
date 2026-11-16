package parking.model;

/** Abstract superclass: shared state (regNo) and the fee template; subclasses supply the rate. */
public abstract class Vehicle {
    private final String regNo;

    protected Vehicle(String regNo) {
        if (regNo == null || regNo.isBlank()) throw new IllegalArgumentException("Registration number required");
        this.regNo = regNo.trim().toUpperCase();
    }
    public final String getRegNo() { return regNo; }
    public abstract String typeName();
    protected abstract double ratePerHour();
    protected double dailyMax() { return 8 * ratePerHour(); }

    /** Every started hour is charged in full (integer ceiling), capped at the daily maximum. */
    public double calculateFee(int minutes) {
        if (minutes < 0) throw new IllegalArgumentException("Duration cannot be negative");
        if (minutes == 0) return 0;                          // slab 0: no time used
        int hours = (minutes + 59) / 60;                     // integer ceiling, no double arithmetic
        double fee = hours * ratePerHour();                  // slab 1: every started hour
        if (fee > dailyMax()) fee = dailyMax();              // slab 2: daily maximum
        return fee;
    }
    @Override public boolean equals(Object o) { return o instanceof Vehicle && regNo.equals(((Vehicle) o).regNo); }
    @Override public int hashCode() { return regNo.hashCode(); }
    @Override public String toString() { return typeName() + "[" + regNo + "]"; }
}
