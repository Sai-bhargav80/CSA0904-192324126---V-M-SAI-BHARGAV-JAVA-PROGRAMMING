package parking.app;

import parking.model.*;
import parking.service.*;

public class Tests {
    static int pass = 0, fail = 0, n = 0;
    static void check(String what, Object expected, Object actual) {
        n++; boolean ok = expected.equals(actual);
        if (ok) pass++; else fail++;
        System.out.printf("T%02d | %-48s | %-34s | %-34s | %s%n", n, what, expected, actual, ok ? "PASS" : "FAIL");
    }
    static String error(Runnable r) {
        try { r.run(); return "no exception"; } catch (RuntimeException e) { return e.getMessage(); }
    }
    public static void main(String[] args) {
        Setup.registerStandardTypes();
        System.out.printf("%-3s | %-48s | %-34s | %-34s | %s%n", "ID", "Test case", "Expected", "Actual", "Result");
        Vehicle bike = new TwoWheeler("TN01A1"), car = new Car("TN02B2"), ev = new ElectricCar("TN03C3");
        check("Normal: two-wheeler 60 min", 10.0, bike.calculateFee(60));
        check("Normal: car 130 min (3 started hours)", 90.0, car.calculateFee(130));
        check("Normal: EV 130 min", 105.0, ev.calculateFee(130));
        check("Normal: EV charging 10 kWh", 120.0, ((Chargeable) ev).chargingFee(10));
        check("Boundary: zero-minute parking", 0.0, car.calculateFee(0));
        check("Boundary: 1 minute is a full hour", 30.0, car.calculateFee(1));
        check("Boundary: exactly 60 min (slab edge)", 30.0, car.calculateFee(60));
        check("Boundary: 61 min (just past slab edge)", 60.0, car.calculateFee(61));
        check("Boundary: exactly 8 h = daily max", 240.0, car.calculateFee(480));
        check("Boundary: 9 h capped at daily max", 240.0, car.calculateFee(540));

        ParkingService small = new ParkingService(new int[]{1}, new ArrayTicketStore());
        ParkingTicket last = small.enter("LAST1", "CAR", 0, false);
        check("Boundary: last free slot -> floor,slot", "0,0", last.getFloor() + "," + last.getSlot());
        check("Invalid: facility full", "Parking full", error(() -> small.enter("LAST2", "CAR", 0, false)));
        check("Invalid: unknown vehicle type", "Unknown vehicle type: TRUCK", error(() -> small.enter("X1", "TRUCK", 0, false)));
        check("Invalid: duplicate registration number", "Vehicle already parked: LAST1", error(() -> small.enter("last1", "CAR", 5, false)));
        ParkingFacility f = new ParkingFacility(new int[]{2, 1});
        check("Invalid: release floor 5", false, f.release(5, 0));
        check("Invalid: release slot -1", false, f.release(0, -1));
        check("Invalid: release already-free slot", false, f.release(0, 0));
        check("Invalid: exit with no ticket", "No active ticket for NOPE", error(() -> small.exit("nope", 10)));
        check("Invalid: exit before entry time", "Exit time before entry time", error(() -> small.exit("LAST1", -1)));
        check("Normal: exit frees the slot (fee 1 h car)", 30.0, small.exit("LAST1", 30));
        ParkingTicket again = small.enter("NEW1", "CAR", 40, false);
        check("Normal: freed slot is reused", "0,0", again.getFloor() + "," + again.getSlot());
        check("Rule: ticket numbers strictly increase", true, again.getTicketNo() > last.getTicketNo());
        ParkingService p2 = new ParkingService(new int[]{1}, new MapTicketStore());
        p2.enter("PASS1", "CAR", 0, true);
        check("Rule: monthly-pass holder parks free", 0.0, p2.exit("PASS1", 600));
        check("Rule: Car equals ElectricCar with same regNo", true, new Car("TN02B2").equals(new ElectricCar("TN02B2")));

        ParkingService day = new ParkingService(new int[]{2, 3}, new MapTicketStore());
        String[][] d = {{"TN01A1", "TWOWHEELER", "540", "600", "false"}, {"TN02B2", "CAR", "540", "670", "false"},
                {"TN03C3", "EV", "550", "551", "false"}, {"TN04D4", "CAR", "600", "900", "true"}, {"TN05E5", "CAR", "600", "1140", "false"}};
        for (String[] r : d) day.enter(r[0], r[1], Integer.parseInt(r[2]), Boolean.parseBoolean(r[4]));
        for (String[] r : d) day.exit(r[0], Integer.parseInt(r[3]));
        check("Scenario: day total vs hand calculation (375)", 375.0, day.getTotalCollection());
        System.out.println("\nPassed " + pass + " / " + (pass + fail));
    }
}
