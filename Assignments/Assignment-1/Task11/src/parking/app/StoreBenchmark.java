package parking.app;

import parking.model.*;
import parking.service.*;
import java.util.*;

/** Task 11 alternative: array vs ArrayList vs HashMap for active tickets. */
public class StoreBenchmark {
    static long usedMemory() { System.gc(); System.gc(); return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(); }

    static void run(int n) {
        ParkingTicket[] tickets = new ParkingTicket[n];
        String[] regs = new String[n];
        for (int i = 0; i < n; i++) {
            regs[i] = "TN" + String.format("%06d", i);
            tickets[i] = new ParkingTicket(new Car(regs[i]), 0, i, 0, false);
        }
        List<String> order = new ArrayList<>(Arrays.asList(regs));
        Collections.shuffle(order, new Random(42));
        String[] names = {"array", "ArrayList", "HashMap"};
        for (int k = 0; k < 3; k++) {
            long m0 = usedMemory();
            TicketStore s = k == 0 ? new ArrayTicketStore() : k == 1 ? new ListTicketStore() : new MapTicketStore();
            for (ParkingTicket t : tickets) s.put(t);
            long m1 = usedMemory();
            long t0 = System.nanoTime();
            int hits = 0;
            for (String r : order) if (s.remove(r) != null) hits++;       // exit processing: find + remove
            long t1 = System.nanoTime();
            System.out.printf("n=%6d %-10s exit of all n vehicles: %8d ms (%d ns per exit), structure memory ~ %d KB, hits=%d%n",
                    n, names[k], (t1 - t0) / 1_000_000, (t1 - t0) / n, Math.max(0, (m1 - m0)) / 1024, hits);
        }
    }
    public static void main(String[] args) {
        run(2_000); run(10_000); run(40_000);
        System.out.println("array/ArrayList: O(n) per exit (O(n^2) for n exits); HashMap: O(1) expected per exit. Space O(n) for all three.");
    }
}
