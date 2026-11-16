import java.util.*;

/** Task 1 - Objects vs parallel arrays: find by regNo and add a new attribute. */
public class Task1Demo {
    // Design A: each vehicle is an object
    static class Vehicle {
        String regNo; String type; long entryTime;
        Vehicle(String r, String t, long e) { regNo = r; type = t; entryTime = e; }
    }

    // Design B: parallel arrays
    static String[] regNo; static String[] type; static long[] entryTime;

    public static void main(String[] args) {
        int n = 100_000;
        Vehicle[] objs = new Vehicle[n];
        regNo = new String[n]; type = new String[n]; entryTime = new long[n];
        for (int i = 0; i < n; i++) {
            String r = "TN" + String.format("%06d", i);
            objs[i] = new Vehicle(r, "CAR", i);
            regNo[i] = r; type[i] = "CAR"; entryTime[i] = i;
        }
        String target = "TN" + String.format("%06d", n - 1); // worst case: last element

        long t0 = System.nanoTime();
        int foundA = -1;
        for (int rep = 0; rep < 200; rep++)
            for (int i = 0; i < n; i++) if (objs[i].regNo.equals(target)) { foundA = i; break; }
        long tA = (System.nanoTime() - t0) / 200;

        t0 = System.nanoTime();
        int foundB = -1;
        for (int rep = 0; rep < 200; rep++)
            for (int i = 0; i < n; i++) if (regNo[i].equals(target)) { foundB = i; break; }
        long tB = (System.nanoTime() - t0) / 200;

        System.out.println("Search (worst case, n = " + n + ")");
        System.out.println("  Objects        : index " + foundA + ", " + tA + " ns per search");
        System.out.println("  Parallel arrays: index " + foundB + ", " + tB + " ns per search");
        System.out.println("Both designs are O(n) for linear search by registration number.");

        System.out.println("\nAdding attribute 'ownerCategory':");
        System.out.println("  Objects        : add ONE field to class Vehicle (1 place changed).");
        System.out.println("  Parallel arrays: declare a new array and update EVERY place that");
        System.out.println("                   inserts, removes or shifts vehicles (many places).");

        String[] regB = {"TN01", "TN02", "TN03"};
        String[] typeB = {"CAR", "BIKE", "EV"};
        regB[0] = regB[1];                          // shift done on one array only
        System.out.println("\nOut-of-sync demo: regNo[0]=" + regB[0] + " but type[0]=" + typeB[0]
                + "  (type no longer matches the vehicle!)");
        System.out.println("Space per vehicle: objects = object header + 3 fields; "
                + "parallel arrays = 3 array slots, no object header. Both O(1) per vehicle, O(n) total.");
    }
}
