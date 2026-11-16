package parking.model;

/** Encapsulated: private state, validating setters, read-only id. */
public class Reservation {
    private static int counter = 0;
    private final int id;                 // never changes after construction
    private int floor;                    // private: only this class touches it
    private int slot;
    private int durationMinutes;
    protected String note = "";           // protected: subclasses and same package
    String auditTag = "NEW";              // default (package-private): parking.model only

    public Reservation(int floor, int slot, int durationMinutes) {
        this.id = ++counter;
        setFloor(floor); setSlot(slot); setDurationMinutes(durationMinutes);
    }
    public int getId() { return id; }
    public int getFloor() { return floor; }
    public int getSlot() { return slot; }
    public int getDurationMinutes() { return durationMinutes; }
    public void setFloor(int floor) {
        if (floor < 0) throw new IllegalArgumentException("Invalid floor: " + floor);
        this.floor = floor;
    }
    public void setSlot(int slot) {
        if (slot < 0) throw new IllegalArgumentException("Invalid slot: " + slot);
        this.slot = slot;
    }
    public void setDurationMinutes(int m) {
        if (m < 0) throw new IllegalArgumentException("Duration cannot be negative: " + m);
        this.durationMinutes = m;
    }
    String audit() { return "audit:" + id + ":" + auditTag; }   // package-private helper
    @Override public String toString() { return "Reservation#" + id + "(floor " + floor + ", slot " + slot + ", " + durationMinutes + " min)"; }
}
