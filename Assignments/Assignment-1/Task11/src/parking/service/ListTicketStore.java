package parking.service;

import parking.model.ParkingTicket;
import java.util.ArrayList;

public class ListTicketStore implements TicketStore {
    private final ArrayList<ParkingTicket> list = new ArrayList<>();
    public void put(ParkingTicket t) { list.add(t); }
    public ParkingTicket find(String reg) { for (ParkingTicket t : list) if (t.getVehicle().getRegNo().equals(reg)) return t; return null; }
    public ParkingTicket remove(String reg) {
        for (int i = 0; i < list.size(); i++) if (list.get(i).getVehicle().getRegNo().equals(reg)) return list.remove(i);   // shifts: O(n)
        return null;
    }
    public int size() { return list.size(); }
}
