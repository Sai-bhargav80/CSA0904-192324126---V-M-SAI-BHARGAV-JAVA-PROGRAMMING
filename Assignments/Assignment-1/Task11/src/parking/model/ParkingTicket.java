package parking.model;

public class ParkingTicket {
    private static int counter = 1000;              // shared by all tickets: numbers never reused
    private final int ticketNo;
    private final Vehicle vehicle;
    private final int floor, slot;
    private final int entryMinute;                  // minutes since midnight
    private final boolean freeParking;              // true for a valid pass holder

    public ParkingTicket(Vehicle vehicle, int floor, int slot, int entryMinute, boolean freeParking) {
        if (vehicle == null) throw new IllegalArgumentException("vehicle required");
        if (entryMinute < 0) throw new IllegalArgumentException("entry time cannot be negative");
        this.ticketNo = ++counter;
        this.vehicle = vehicle; this.floor = floor; this.slot = slot;
        this.entryMinute = entryMinute; this.freeParking = freeParking;
    }
    public int getTicketNo() { return ticketNo; }
    public Vehicle getVehicle() { return vehicle; }
    public int getFloor() { return floor; }
    public int getSlot() { return slot; }
    public int getEntryMinute() { return entryMinute; }
    public boolean isFreeParking() { return freeParking; }
    @Override public String toString() {
        return "Ticket#" + ticketNo + " " + vehicle + " floor " + floor + " slot " + slot + " in=" + entryMinute;
    }
}
