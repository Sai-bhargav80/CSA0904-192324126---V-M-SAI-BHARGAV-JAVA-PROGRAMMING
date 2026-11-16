/** Task 6 - hierarchy: Vehicle -> Car -> ElectricCar, with constructor trace. */
abstract class Vehicle {
    private final String regNo;
    Vehicle(String regNo) { this.regNo = regNo; System.out.println("  Vehicle(" + regNo + ") constructor"); }
    String getRegNo() { return regNo; }
    abstract double ratePerHour();
    double fee(int hours) { return hours * ratePerHour(); }
}
class TwoWheeler extends Vehicle {
    TwoWheeler(String r) { super(r); System.out.println("  TwoWheeler constructor"); }
    double ratePerHour() { return 10; }
}
class Car extends Vehicle {
    protected int seats;
    Car(String r) { super(r); seats = 5; System.out.println("  Car constructor"); }
    double ratePerHour() { return 30; }
}
class ElectricCar extends Car {
    private int batteryKWh;
    ElectricCar(String r, int kWh) { super(r); batteryKWh = kWh; System.out.println("  ElectricCar constructor"); }
    @Override double ratePerHour() { return super.ratePerHour() + 5; }    // super used for a method
    int getBattery() { return batteryKWh; }
}

/** Composition alternative: Vehicle HAS-A VehicleType. */
class VehicleType {
    final String name; final double rate;
    VehicleType(String n, double r) { name = n; rate = r; }
}
class CVehicle {
    private final String regNo; private final VehicleType type;
    CVehicle(String r, VehicleType t) { regNo = r; type = t; }
    double fee(int hours) { return hours * type.rate; }
}

public class Task6Demo {
    public static void main(String[] args) {
        System.out.println("Creating ElectricCar(\"TN09EV0001\", 40):");
        ElectricCar e = new ElectricCar("TN09EV0001", 40);
        System.out.println("Fee for 3 h: Rs " + e.fee(3) + "  (seats inherited = " + e.seats + ")");

        // composition: adding a new type = new data object, NO new class
        CVehicle ebike = new CVehicle("TN10EB0001", new VehicleType("E-BIKE", 15));
        System.out.println("Composition e-bike, 3 h: Rs " + ebike.fee(3));

        long t0 = System.nanoTime(); double s = 0;
        Vehicle v = e;
        for (int i = 0; i < 50_000_000; i++) s += v.ratePerHour();
        long t1 = System.nanoTime();
        VehicleType vt = new VehicleType("EV", 35);
        for (int i = 0; i < 50_000_000; i++) s += vt.rate;
        long t2 = System.nanoTime();
        System.out.println("50M fee-rate lookups: inheritance " + (t1 - t0) / 1_000_000 + " ms, composition "
                + (t2 - t1) / 1_000_000 + " ms (both O(1)) [" + (s > 0) + "]");
    }
}
