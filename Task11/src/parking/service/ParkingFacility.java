package parking.service;

/** Jagged array: floor f has slotsPerFloor[f] slots; null = free, otherwise the vehicle number. */
public class ParkingFacility {
    private final String[][] slots;
    public ParkingFacility(int[] slotsPerFloor) {
        slots = new String[slotsPerFloor.length][];
        for (int f = 0; f < slotsPerFloor.length; f++) {
            if (slotsPerFloor[f] <= 0) throw new IllegalArgumentException("A floor needs at least one slot");
            slots[f] = new String[slotsPerFloor[f]];
        }
    }
    /** Returns {floor, slot} of the first free slot, or null when full. */
    public int[] allocate(String regNo) {
        for (int f = 0; f < slots.length; f++)
            for (int s = 0; s < slots[f].length; s++)
                if (slots[f][s] == null) { slots[f][s] = regNo; return new int[]{f, s}; }
        return null;
    }
    public boolean release(int floor, int slot) {
        if (floor < 0 || floor >= slots.length) return false;
        if (slot < 0 || slot >= slots[floor].length) return false;
        if (slots[floor][slot] == null) return false;
        slots[floor][slot] = null;
        return true;
    }
    public int capacity() { int c = 0; for (String[] f : slots) c += f.length; return c; }
    public int floors() { return slots.length; }
}
