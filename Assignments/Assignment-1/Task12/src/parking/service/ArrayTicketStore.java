package parking.service;

import parking.model.ParkingTicket;
import java.util.Arrays;

/** Plain array + linear search (the baseline named in Task 11). */
public class ArrayTicketStore implements TicketStore {
    private ParkingTicket[] data = new ParkingTicket[16];
    private int n = 0;
    public void put(ParkingTicket t) { if (n == data.length) data = Arrays.copyOf(data, n * 2); data[n++] = t; }
    private int indexOf(String reg) { for (int i = 0; i < n; i++) if (data[i].getVehicle().getRegNo().equals(reg)) return i; return -1; }
    public ParkingTicket find(String reg) { int i = indexOf(reg); return i < 0 ? null : data[i]; }
    public ParkingTicket remove(String reg) {
        int i = indexOf(reg); if (i < 0) return null;
        ParkingTicket t = data[i]; data[i] = data[--n]; data[n] = null; return t;     // swap with last: O(1) after the search
    }
    public int size() { return n; }
}
