package parking.service;

import parking.model.PublicReservation;
import parking.model.Reservation;

public class ReservationService {
    /** Only the public API of Reservation is usable here: r.audit() or r.note would NOT compile (other package). */
    public String describe(Reservation r) { return r.toString(); }

    // Alternative design: every caller repeats (or forgets, or changes) the check
    public void bookByCounter(PublicReservation r, int floor) { if (floor < 0) throw new IllegalArgumentException("bad floor"); r.floor = floor; }
    public void bookByApp(PublicReservation r, int floor)     { r.floor = floor; }                                   // forgot the check
    public void bookByKiosk(PublicReservation r, int floor)   { if (floor < 0 || floor > 4) throw new IllegalArgumentException("bad floor"); r.floor = floor; } // different rule!
}
