package parking.model;

/** NEW (Task 12): a pass that can make parking free on given days. */
public interface Pass {
    boolean coversParking(int day);
    double price();            // amount charged when the pass is sold at the gate
    String name();
}
