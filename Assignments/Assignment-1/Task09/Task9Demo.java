interface Chargeable {
    double chargingFee(int kWh);
    default String chargingNote() { return "Charging billed separately from parking"; }   // default method
    static double gst(double amount) { return amount * 0.18; }                             // static method
}
interface Payable { double amountDue(); }

abstract class Vehicle {                      // abstract class: shared STATE + constructor + template method
    private final String regNo;
    protected Vehicle(String r) { regNo = r; }
    String getRegNo() { return regNo; }
    protected abstract double ratePerHour();   // WHAT differs per type
    double fee(int minutes) { return ((minutes + 59) / 60) * ratePerHour(); }    // HOW is shared
}
class Car extends Vehicle implements Payable {
    private int minutes;
    Car(String r, int m) { super(r); minutes = m; }
    protected double ratePerHour() { return 30; }
    public double amountDue() { return fee(minutes); }
}
class ElectricCar extends Car implements Chargeable {         // multiple behaviours via interfaces
    ElectricCar(String r, int m) { super(r, m); }
    @Override protected double ratePerHour() { return 35; }
    public double chargingFee(int kWh) { return kWh * 12; }
}

/* ---- Alternative: Vehicle as interface (no state / constructor), Chargeable as abstract class ---- */
interface IVehicle {
    String regNo();                            // no field allowed: every class must store its own
    double ratePerHour();
    default double fee(int minutes) { return ((minutes + 59) / 60) * ratePerHour(); }
}
abstract class ChargeableBase { abstract double chargingFee(int kWh); }
class Ev2 extends ChargeableBase implements IVehicle {          // can extend only ONE class
    private final String reg; Ev2(String r) { reg = r; }
    public String regNo() { return reg; }
    public double ratePerHour() { return 35; }
    double chargingFee(int kWh) { return kWh * 12; }
}

public class Task9Demo {
    public static void main(String[] args) {
        ElectricCar e = new ElectricCar("TN09EV0001", 130);
        System.out.println("Parking Rs " + e.amountDue() + ", charging 10 kWh Rs " + e.chargingFee(10));
        System.out.println(e.chargingNote() + "; GST on charging = Rs " + Math.round(Chargeable.gst(e.chargingFee(10)) * 100) / 100.0);
        // Vehicle v = new Vehicle("X");   // compile error: cannot instantiate an abstract class
        Ev2 e2 = new Ev2("TN09EV0002");
        System.out.println("Interface-based EV: Rs " + e2.fee(130) + " (state 'reg' had to be re-declared in each class)");
        System.out.println("Method call cost: abstract-class call is invokevirtual, interface call is invokeinterface; both O(1).");
    }
}
