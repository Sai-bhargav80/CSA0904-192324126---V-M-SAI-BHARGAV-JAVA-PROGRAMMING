import java.util.*;

/** Alternative concept: free-count per floor + stack of free slot numbers per floor. */
public class FastAllocator {
    private final String[][] slots;
    private final int[] freeCount;
    private final ArrayDeque<Integer>[] freeStack;

    @SuppressWarnings("unchecked")
    public FastAllocator(int[] slotsPerFloor) {
        slots = new String[slotsPerFloor.length][];
        freeCount = new int[slotsPerFloor.length];
        freeStack = new ArrayDeque[slotsPerFloor.length];
        for (int f = 0; f < slotsPerFloor.length; f++) {
            slots[f] = new String[slotsPerFloor[f]];
            freeCount[f] = slotsPerFloor[f];
            freeStack[f] = new ArrayDeque<>();
            for (int s = slotsPerFloor[f] - 1; s >= 0; s--) freeStack[f].push(s); // lowest on top
        }
    }
    public int[] allocate(String v) {                     // O(F)
        for (int f = 0; f < slots.length; f++) {
            if (freeCount[f] > 0) {                       // skip a full floor in O(1)
                int s = freeStack[f].pop();               // O(1)
                slots[f][s] = v; freeCount[f]--;
                return new int[]{f, s};
            }
        }
        return null;
    }
    public boolean release(int f, int s) {                // O(1)
        if (f < 0 || f >= slots.length || s < 0 || s >= slots[f].length || slots[f][s] == null) return false;
        slots[f][s] = null; freeCount[f]++; freeStack[f].push(s);
        return true;
    }

    public static void main(String[] args) {
        FastAllocator a = new FastAllocator(new int[]{2, 1});
        System.out.println(Arrays.toString(a.allocate("1")) + " " + Arrays.toString(a.allocate("2")) + " "
                + Arrays.toString(a.allocate("3")) + " " + Arrays.toString(a.allocate("4")));
        System.out.println(a.release(0, 1) + " " + a.release(0, 1) + " " + a.release(5, 0)
                + " " + Arrays.toString(a.allocate("4")));

        int F = 5, S = 2000;
        int[] cfg = new int[F]; Arrays.fill(cfg, S);
        ParkingFacility slow = new ParkingFacility(cfg);
        FastAllocator fast = new FastAllocator(cfg);
        for (int i = 0; i < 4 * S; i++) { slow.allocate("V" + i); fast.allocate("V" + i); }
        int reps = 2000;
        for (int i = 0; i < 500; i++) { int[] r = slow.allocate("W"); slow.release(r[0], r[1]); r = fast.allocate("W"); fast.release(r[0], r[1]); } // warm-up
        long t0 = System.nanoTime();
        for (int i = 0; i < reps; i++) { int[] r = slow.allocate("X"); slow.release(r[0], r[1]); }
        long tSlow = (System.nanoTime() - t0) / reps;
        t0 = System.nanoTime();
        for (int i = 0; i < reps; i++) { int[] r = fast.allocate("X"); fast.release(r[0], r[1]); }
        long tFast = (System.nanoTime() - t0) / reps;
        System.out.println("\nallocate+release with floors 0-3 full (F=5, S=2000):");
        System.out.println("  linear scan   : " + tSlow + " ns");
        System.out.println("  count + stack : " + tFast + " ns");
    }
}
