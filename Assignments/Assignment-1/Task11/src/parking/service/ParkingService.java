package parking.service;

import parking.model.ParkingException;
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

    public double exit(String regNo, int exitMinute) {
        String key = regNo == null ? "" : regNo.trim().toUpperCase();
        ParkingTicket t = store.find(key);
        if (t == null) throw new ParkingException("No active ticket for " + key);
        int minutes = exitMinute - t.getEntryMinute();
        if (minutes < 0) throw new ParkingException("Exit time before entry time");
        double fee = t.isFreeParking() ? 0 : t.getVehicle().calculateFee(minutes);
        facility.release(t.getFloor(), t.getSlot());
        store.remove(key);
        totalCollection += fee;
        return fee;
    }
    public double getTotalCollection() { return totalCollection; }
    public int parkedCount() { return store.size(); }
    public ParkingFacility getFacility() { return facility; }
}
