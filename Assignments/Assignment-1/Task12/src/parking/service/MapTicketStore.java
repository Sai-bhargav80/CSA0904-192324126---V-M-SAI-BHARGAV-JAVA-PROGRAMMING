package parking.service;

import parking.model.ParkingTicket;
import java.util.HashMap;

public class MapTicketStore implements TicketStore {
    private final HashMap<String, ParkingTicket> map = new HashMap<>();
    public void put(ParkingTicket t) { map.put(t.getVehicle().getRegNo(), t); }
    public ParkingTicket find(String reg) { return map.get(reg); }
    public ParkingTicket remove(String reg) { return map.remove(reg); }
    public int size() { return map.size(); }
}
