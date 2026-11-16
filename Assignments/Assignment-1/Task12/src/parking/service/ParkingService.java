package parking.service;

import parking.model.ParkingException;
import parking.model.Pass;
import parking.model.ParkingTicket;
import parking.model.Vehicle;
import parking.model.VehicleFactory;

/** Orchestrates entry and exit; owns the business rules. */
public class ParkingService {
    private final ParkingFacility facility;
    private final TicketStore store;
    private double totalCollection = 0;

    public ParkingService(int[] slotsPerFloor, TicketStore store) {
        this.facility = new ParkingFacility(slotsPerFloor);
        this.store = store;
    }

    public ParkingTicket enter(String regNo, String type, int entryMinute, boolean passHolder) {
        Vehicle v = VehicleFactory.create(type, regNo);                 // unknown type -> exception
        if (store.find(v.getRegNo()) != null) throw new ParkingException("Vehicle already parked: " + v.getRegNo());
        int[] pos = facility.allocate(v.getRegNo());
        if (pos == null) throw new ParkingException("Parking full");
        ParkingTicket t = new ParkingTicket(v, pos[0], pos[1], entryMinute, passHolder);
        store.put(t);
        return t;
    }

    /** NEW (Task 12): entry with a Pass (monthly / one-day). Delegates to the unchanged enter(...). */
    public ParkingTicket enter(String regNo, String type, int entryMinute, Pass pass, int today) {
        return enter(regNo, type, entryMinute, pass != null && pass.coversParking(today));
    }
    /** NEW (Task 12): money taken at the gate for selling a pass (e.g. a one-day visitor pass). */
    public void sellPass(Pass pass) { totalCollection += pass.price(); }

    public double exit(String regNo, int exitMinute) {
        String key = regNo == null ? "" : regNo.trim().toUpperCase();
        ParkingTicket t = store.find(key);
        if (t == null) throw new ParkingException("No active ticket for " + key);
        int minutes = exitMinute - t.getEntryMinute();
        if (minutes < 0) throw new ParkingException("Exit time before entry time");
        double fee = t.isFreeParking() ? 0 : t.getVehicle().calculateFee(t.getEntryMinute(), exitMinute)   /* Task 12: was calculateFee(minutes) */;
        facility.release(t.getFloor(), t.getSlot());
        store.remove(key);
        totalCollection += fee;
        return fee;
    }
    public double getTotalCollection() { return totalCollection; }
    public int parkedCount() { return store.size(); }
    public ParkingFacility getFacility() { return facility; }
}
