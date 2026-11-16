import java.util.*;

abstract class Vehicle {
    private final String regNo;
    protected static final double TAX_PERCENT = 0;        // final variable (constant)
    Vehicle(String regNo) { this.regNo = regNo; }
    final String getRegNo() { return regNo; }              // final method: cannot be overridden
    protected abstract double ratePerHour();
    public double calculateFee(int minutes) { return ((minutes + 59) / 60) * ratePerHour(); }
    @Override public String toString() { return getClass().getSimpleName() + "[" + regNo + "]"; }
    @Override public boolean equals(Object o) { return o instanceof Vehicle && regNo.equals(((Vehicle) o).regNo); }
    @Override public int hashCode() { return regNo.hashCode(); }
}
class TwoWheeler extends Vehicle {
    TwoWheeler(String r) { super(r); }
    @Override protected double ratePerHour() { return 10; }
}
class Car extends Vehicle {
    Car(String r) { super(r); }
    @Override protected double ratePerHour() { return 30; }
}
final class DeliveryVan extends Vehicle {                   // final class: no further subclassing
    DeliveryVan(String r) { super(r); }
    @Override protected double ratePerHour() { return 60; }
    @Override public double calculateFee(int minutes) { return super.calculateFee(minutes) + 5; } // specialised
}

/** Alternative: one method, switch on type field. */
class SwitchVehicle {
    final String regNo; final String type;
    SwitchVehicle(String r, String t) { regNo = r; type = t; }
    double calculateFee(int minutes) {
        int h = (minutes + 59) / 60;
        switch (type) {
            case "BIKE": return h * 10;
            case "CAR":  return h * 30;
            case "VAN":  return h * 60 + 5;
            default: throw new IllegalArgumentException("unknown type " + type);   // every new type edits THIS method
        }
    }
}

public class Task7Demo {
    public static void main(String[] args) {
        Vehicle[] vs = { new TwoWheeler("TN01A1"), new Car("TN02B2"), new DeliveryVan("TN03C3") };
        for (Vehicle v : vs) System.out.println(v + " 130 min -> Rs " + v.calculateFee(130));

        Set<Vehicle> set = new HashSet<>();
        set.add(new Car("TN02B2"));
        System.out.println("\nequals same reg  : " + new Car("TN02B2").equals(new Car("TN02B2")));
        System.out.println("hashCode equal   : " + (new Car("TN02B2").hashCode() == new Car("TN02B2").hashCode()));
        System.out.println("HashSet contains new Car(\"TN02B2\"): " + set.contains(new Car("TN02B2")));
        System.out.println("duplicate add rejected: " + !set.add(new Car("TN02B2")));

        SwitchVehicle sv = new SwitchVehicle("TN03C3", "VAN");
        System.out.println("\nSwitch version of the van, 130 min -> Rs " + sv.calculateFee(130));
        try { new SwitchVehicle("X", "EBIKE").calculateFee(60); }
        catch (IllegalArgumentException e) { System.out.println("Switch version fails for a new type: " + e.getMessage()); }
        System.out.println("Overriding: adding a type = 1 new class, 0 changed. Switch: 1 existing method edited, retest all types.");
        System.out.println("Time per fee: overriding O(1); switch on String O(1) (hash) / O(k) with an if-else chain.");
    }
}
