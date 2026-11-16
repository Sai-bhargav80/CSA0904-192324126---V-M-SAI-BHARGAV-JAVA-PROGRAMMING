package parking.service;

import parking.model.ParkingTicket;

/** Where active tickets live. Three implementations are compared in StoreBenchmark. */
public interface TicketStore {
    void put(ParkingTicket t);
    ParkingTicket find(String regNo);
    ParkingTicket remove(String regNo);
    int size();
}
