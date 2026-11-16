package parking.model;

/** Staff / student pass, bought in advance (no gate revenue). Valid from fromDay to toDay inclusive. */
public class MonthlyPass implements Pass {
    private final int fromDay, toDay;
    public MonthlyPass(int fromDay, int toDay) {
        if (toDay < fromDay) throw new IllegalArgumentException("pass end before start");
        this.fromDay = fromDay; this.toDay = toDay;
    }
    @Override public boolean coversParking(int day) { return day >= fromDay && day <= toDay; }
    @Override public double price() { return 0; }
    @Override public String name() { return "MONTHLY"; }
}
