package parking.model;

/** NEW vehicle category: car rate, doubled for every started hour that begins between 9 AM and 5 PM. */
public class DeliveryVan extends Vehicle {
    private static final int PEAK_START = 9 * 60, PEAK_END = 17 * 60;     // [09:00, 17:00)
    public DeliveryVan(String regNo) { super(regNo); }
    @Override public String typeName() { return "VAN"; }
    @Override protected double ratePerHour() { return 30; }                // off-peak = car rate
    @Override protected double dailyMax() { return 16 * ratePerHour(); }   // 8 hours at the peak rate
    static boolean isPeak(int minuteOfDay) { int m = minuteOfDay % 1440; return m >= PEAK_START && m < PEAK_END; }
    @Override public double calculateFee(int entryMinute, int exitMinute) {
        int minutes = exitMinute - entryMinute;
        if (minutes < 0) throw new IllegalArgumentException("Duration cannot be negative");
        if (minutes == 0) return 0;
        int hours = (minutes + 59) / 60;
        double fee = 0;
        for (int h = 0; h < hours; h++)
            fee += isPeak(entryMinute + 60 * h) ? 2 * ratePerHour() : ratePerHour();
        return Math.min(fee, dailyMax());
    }
}
