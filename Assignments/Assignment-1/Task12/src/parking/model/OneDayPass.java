package parking.model;

/** Visitor one-day pass, Rs 100, valid only on the day of purchase. */
public class OneDayPass implements Pass {
    public static final double PRICE = 100;
    private final int day;
    public OneDayPass(int day) { this.day = day; }
    @Override public boolean coversParking(int d) { return d == day; }
    @Override public double price() { return PRICE; }
    @Override public String name() { return "ONE-DAY"; }
}
