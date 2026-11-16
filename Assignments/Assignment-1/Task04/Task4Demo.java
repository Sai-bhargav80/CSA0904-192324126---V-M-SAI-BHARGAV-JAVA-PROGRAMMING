import java.util.*;

/** Task 4 - classes, constructors, this, chaining, copy constructor, alternatives. */
class ParkingTicket {
    private final int ticketNo;
    private final String regNo;
    private final String vehicleType;
    private final long entryMinute;          // minutes since midnight of the simulation day
    private final boolean monthlyPass;
    private static int counter = 1000;

    // default (no-arg) constructor -> chained to the full one with safe defaults
    ParkingTicket() { this("UNKNOWN", "CAR"); }
    // overloaded: reg + type, entry time = 0, no pass
    ParkingTicket(String regNo, String vehicleType) { this(regNo, vehicleType, 0); }
    // overloaded: adds entry time
    ParkingTicket(String regNo, String vehicleType, long entryMinute) { this(regNo, vehicleType, entryMinute, false); }
    // master constructor: all validation lives here (single place)
    ParkingTicket(String regNo, String vehicleType, long entryMinute, boolean monthlyPass) {
        if (regNo == null || regNo.isBlank()) throw new IllegalArgumentException("regNo required");
        if (entryMinute < 0) throw new IllegalArgumentException("entry time cannot be negative");
        this.ticketNo = ++counter;
        this.regNo = regNo;
        this.vehicleType = vehicleType;
        this.entryMinute = entryMinute;
        this.monthlyPass = monthlyPass;
    }
    // copy constructor: re-issue a duplicate ticket (same details, NEW unique number)
    ParkingTicket(ParkingTicket other) {
        this(other.regNo, other.vehicleType, other.entryMinute, other.monthlyPass);
    }
    // static factory alternative
    static ParkingTicket forVisitor(String regNo, String type, long entry) { return new ParkingTicket(regNo, type, entry, false); }

    int getTicketNo() { return ticketNo; }
    String getRegNo() { return regNo; }
    @Override public String toString() {
        return "Ticket#" + ticketNo + "[" + regNo + "," + vehicleType + ",in=" + entryMinute + ",pass=" + monthlyPass + "]";
    }
}

/** Alternative 1: default constructor + setters (object can exist in invalid state). */
class MutableTicket {
    int ticketNo; String regNo; String vehicleType; long entryMinute;
    void setRegNo(String r) { regNo = r; }
    void setVehicleType(String t) { vehicleType = t; }
    void setEntryMinute(long e) { entryMinute = e; }
}

public class Task4Demo {
    public static void main(String[] args) {
        ParkingTicket a = new ParkingTicket();
        ParkingTicket b = new ParkingTicket("TN01AB1111", "CAR");
        ParkingTicket c = new ParkingTicket("TN02CD2222", "EV", 540, true);
        ParkingTicket d = new ParkingTicket(c);                 // copy constructor
        ParkingTicket e = ParkingTicket.forVisitor("TN03EF3333", "BIKE", 600);
        System.out.println(a); System.out.println(b); System.out.println(c); System.out.println(d); System.out.println(e);
        System.out.println("c == d ? " + (c == d) + "   (different objects, different ticket numbers)");

        // memory: reference variables on stack, objects on heap
        ParkingTicket alias = c;
        System.out.println("alias == c ? " + (alias == c) + "   (two references, ONE heap object)");

        // validity guarantee
        try { new ParkingTicket("", "CAR"); } catch (IllegalArgumentException ex) { System.out.println("Constructor rejected: " + ex.getMessage()); }
        MutableTicket m = new MutableTicket();                  // legal but invalid: regNo == null
        System.out.println("Setter-style object created with regNo = " + m.regNo + " (invalid object exists!)");

        // cost of creating n tickets
        int n = 1_000_000;
        for (int w = 0; w < 3; w++) for (int i = 0; i < 200_000; i++) { new ParkingTicket("W" + i, "CAR", i); }
        long t0 = System.nanoTime();
        ParkingTicket[] arr = new ParkingTicket[n];
        for (int i = 0; i < n; i++) arr[i] = new ParkingTicket("R", "CAR", i, false);
        long t1 = System.nanoTime();
        MutableTicket[] arr2 = new MutableTicket[n];
        for (int i = 0; i < n; i++) { MutableTicket t = new MutableTicket(); t.setRegNo("R"); t.setVehicleType("CAR"); t.setEntryMinute(i); arr2[i] = t; }
        long t2 = System.nanoTime();
        ParkingTicket[] arr3 = new ParkingTicket[n];
        for (int i = 0; i < n; i++) arr3[i] = ParkingTicket.forVisitor("R", "CAR", i);
        long t3 = System.nanoTime();
        System.out.println("\nCreate n = " + n + " tickets:");
        System.out.println("  chained constructors : " + (t1 - t0) / 1_000_000 + " ms");
        System.out.println("  default + setters    : " + (t2 - t1) / 1_000_000 + " ms");
        System.out.println("  static factory       : " + (t3 - t2) / 1_000_000 + " ms");
        System.out.println("All three are O(n) time and O(n) space (O(1) per ticket).");
    }
}
