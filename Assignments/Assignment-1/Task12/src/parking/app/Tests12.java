package parking.app;

import parking.model.*;
import parking.service.*;

public class Tests12 {
    static int pass = 0, fail = 0, n = 0;
    static void check(String what, Object expected, Object actual) {
        n++; boolean ok = expected.equals(actual);
        if (ok) pass++; else fail++;
        System.out.printf("N%02d | %-52s | %-26s | %-26s | %s%n", n, what, expected, actual, ok ? "PASS" : "FAIL");
    }
    static String error(Runnable r) { try { r.run(); return "no exception"; } catch (RuntimeException e) { return e.getMessage(); } }

    public static void main(String[] args) {
        ExtendedSetup.registerAll();
        System.out.printf("%-3s | %-52s | %-26s | %-26s | %s%n", "ID", "Test case (new requirements)", "Expected", "Actual", "Result");
        Vehicle eb = new EBike("EB9"), van = new DeliveryVan("V9");
        check("E-bike: 45 min flat fee", 20.0, eb.calculateFee(45));
        check("E-bike: 600 min still flat", 20.0, eb.calculateFee(600));
        check("E-bike: zero minutes", 0.0, eb.calculateFee(0));
        check("Van off-peak 08:00-09:00 (hour starts 08:00)", 30.0, van.calculateFee(480, 540));
        check("Van peak 10:00-11:00", 60.0, van.calculateFee(600, 660));
        check("Van mixed 08:30-10:45 (30+60+60)", 150.0, van.calculateFee(510, 645));
        check("Van boundary: hour starts exactly 09:00 = peak", 60.0, van.calculateFee(540, 600));
        check("Van boundary: hour starts exactly 17:00 = off-peak", 30.0, van.calculateFee(1020, 1080));
        check("Van boundary: hour starts 16:59 = peak", 60.0, van.calculateFee(1019, 1079));
        check("Van boundary: hour starts 08:59 = off-peak", 30.0, van.calculateFee(539, 599));
        check("Van: 09:00-18:00 capped at daily max", 480.0, van.calculateFee(540, 1080));
        check("Van: zero minutes", 0.0, van.calculateFee(600, 600));
        check("Van polymorphic via Vehicle reference", 60.0, ((Vehicle) new DeliveryVan("V8")).calculateFee(600, 660));
        check("Regression: car 130 min unchanged", 90.0, new Car("C9").calculateFee(100, 230));
        check("Regression: EV 130 min unchanged", 105.0, new ElectricCar("E9").calculateFee(0, 130));
        check("Regression: 2-arg default equals 1-arg for car", new Car("C9").calculateFee(61), new Car("C9").calculateFee(0, 61));

        int today = 3;
        check("One-day pass valid on purchase day", true, new OneDayPass(3).coversParking(today));
        check("One-day pass invalid next day", false, new OneDayPass(3).coversParking(4));
        check("Monthly pass valid inside range", true, new MonthlyPass(1, 30).coversParking(30));
        check("Monthly pass expired after range", false, new MonthlyPass(1, 30).coversParking(31));
        check("Invalid pass range rejected", "pass end before start", error(() -> new MonthlyPass(5, 1)));

        ParkingService s1 = new ParkingService(new int[]{4}, new MapTicketStore());
        s1.enter("P1", "CAR", 0, new OneDayPass(3), 3);
        check("Visitor with valid one-day pass parks free", 0.0, s1.exit("P1", 300));
        s1.enter("P2", "CAR", 0, new OneDayPass(3), 4);
        check("Visitor with expired one-day pass pays", 150.0, s1.exit("P2", 300));
        s1.sellPass(new OneDayPass(3));
        check("Pass sale counted in collection (150 + 100)", 250.0, s1.getTotalCollection());

        ParkingService s5 = new ParkingService(new int[]{2, 3, 1, 4, 2}, new ArrayTicketStore());
        String where = "";
        for (int i = 0; i < 12; i++) { ParkingTicket t = s5.enter("R" + i, "CAR", 0, false); where += t.getFloor() + ":" + t.getSlot() + " "; }
        check("5 floors, unequal sizes: allocation order", "0:0 0:1 1:0 1:1 1:2 2:0 3:0 3:1 3:2 3:3 4:0 4:1 ", where);
        check("5 floors: 13th vehicle rejected (capacity 12)", "Parking full", error(() -> s5.enter("R12", "CAR", 0, false)));
        check("Unknown type still rejected", "Unknown vehicle type: BUS", error(() -> s5.enter("B1", "BUS", 0, false)));

        ParkingService day = new ParkingService(new int[]{2, 3, 1, 4, 2}, new MapTicketStore());
        Pass vp = new OneDayPass(today), sp = new MonthlyPass(1, 30);
        day.sellPass(vp);
        day.enter("EB1", "EBIKE", 540, null, today); day.enter("V1", "VAN", 510, null, today);
        day.enter("C1", "CAR", 540, null, today);    day.enter("C2", "CAR", 600, vp, today); day.enter("T1", "TWOWHEELER", 600, sp, today);
        day.exit("EB1", 600); day.exit("V1", 645); day.exit("C1", 670); day.exit("C2", 900); day.exit("T1", 900);
        check("Scenario: day total vs hand calculation (360)", 360.0, day.getTotalCollection());
        System.out.println("\nPassed " + pass + " / " + (pass + fail));
    }
}
