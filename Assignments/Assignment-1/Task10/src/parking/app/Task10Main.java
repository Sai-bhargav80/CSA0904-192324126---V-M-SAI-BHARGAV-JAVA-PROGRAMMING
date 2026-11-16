package parking.app;

import parking.model.PublicReservation;
import parking.model.Reservation;
import parking.service.ReservationService;

public class Task10Main {
    public static void main(String[] args) {
        ReservationService svc = new ReservationService();
        Reservation r = new Reservation(1, 7, 90);
        System.out.println(svc.describe(r));
        try { r.setDurationMinutes(-5); } catch (IllegalArgumentException e) { System.out.println("Rejected: " + e.getMessage()); }
        try { r.setSlot(-1); }            catch (IllegalArgumentException e) { System.out.println("Rejected: " + e.getMessage()); }
        System.out.println("State unchanged after rejected updates: " + r);

        Integer a = 127, b = 127, c = 1000, d = 1000;          // autoboxing int -> Integer
        System.out.println("\nInteger 127 == 127   : " + (a == b) + "   (cached objects)");
        System.out.println("Integer 1000 == 1000 : " + (c == d) + "  (two different objects!)");
        System.out.println("Integer 1000 equals  : " + c.equals(d) + "   <- always use equals() for wrappers");
        Integer slotOrNull = null;
        try { int x = slotOrNull; } catch (NullPointerException e) { System.out.println("Auto-unboxing null -> NullPointerException"); }

        PublicReservation p = new PublicReservation();
        svc.bookByApp(p, -3);                                  // nothing stops this
        System.out.println("\nPublic-field design accepted floor = " + p.floor + " (invalid state!)");
        System.out.println("Validation cost: O(1) per update in both designs; setter = 1 place, public fields = every caller.");
    }
}
