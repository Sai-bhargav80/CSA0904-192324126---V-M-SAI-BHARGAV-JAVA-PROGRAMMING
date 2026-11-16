import java.util.*;

/** Task 2 - data types, jagged array, casting risk and Scanner input. */
public class Task2Demo {
    static final double RATE_PER_HOUR = 30.0;

    // WRONG: integer division drops the part of the hour
    static double wrongFee(int minutes) { return (minutes / 60) * RATE_PER_HOUR; }
    // WRONG: cast applied AFTER the integer division - the damage is already done
    static double wrongFee2(int minutes) { return (double) (minutes / 60) * RATE_PER_HOUR; }
    // CORRECT: ceiling of minutes/60 using integer arithmetic only
    static double correctFee(int minutes) { return ((minutes + 59) / 60) * RATE_PER_HOUR; }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("minutes | wrong (int div) | wrong (late cast) | correct (ceil)");
        while (sc.hasNextInt()) {
            int m = sc.nextInt();
            System.out.printf("%7d | %15.1f | %17.1f | %14.1f%n", m, wrongFee(m), wrongFee2(m), correctFee(m));
        }
        long entryMillis = 1_700_000_000_000L;
        int narrowed = (int) entryMillis;
        System.out.println("\nlong -> int narrowing: " + entryMillis + " becomes " + narrowed + " (overflow!)");

        int[] slotsPerFloor = {20, 50, 100};
        String[][] slots = new String[slotsPerFloor.length][];
        int total = 0;
        for (int f = 0; f < slots.length; f++) { slots[f] = new String[slotsPerFloor[f]]; total += slotsPerFloor[f]; }
        System.out.println("Jagged array: floors = " + slots.length + ", total slots = " + total);
        for (int f = 0; f < slots.length; f++) System.out.println("  floor " + f + " has " + slots[f].length + " slots");

        int[] offset = new int[slotsPerFloor.length + 1];
        for (int f = 0; f < slotsPerFloor.length; f++) offset[f + 1] = offset[f] + slotsPerFloor[f];
        String[] flat = new String[offset[slotsPerFloor.length]];
        int f = 1, s = 7;
        flat[offset[f] + s] = "TN01AB1234";
        System.out.println("Flat array index of (floor 1, slot 7) = " + (offset[f] + s)
                + "  (floor*slotsPerFloor+slot only works when every floor has equal slots)");
    }
}
