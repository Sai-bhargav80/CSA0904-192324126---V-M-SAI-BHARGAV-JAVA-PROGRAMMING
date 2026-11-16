import java.time.LocalTime;

/** Compile-time (overloading) and run-time (overriding) polymorphism + instanceof alternative. */
abstract class PVehicle {
    abstract double rate();
    // overloaded: the compiler picks by the ARGUMENT list at compile time
    double calculateFee(int minutes) { return ((minutes + 59) / 60) * rate(); }
    double calculateFee(LocalTime in, LocalTime out) {
        return calculateFee((int) java.time.Duration.between(in, out).toMinutes());
    }
}
class PBike extends PVehicle { double rate() { return 10; } }
class PCar  extends PVehicle { double rate() { return 30; } }
class PEv   extends PCar     { @Override double rate() { return 35; } }

public class PolymorphismDemo {
    // Alternative: instanceof chain - must be edited for every new type
    static double feeByInstanceof(PVehicle v, int minutes) {
        int h = (minutes + 59) / 60;
        if (v instanceof PEv) return h * 35;         // order matters: subclass must be tested first!
        else if (v instanceof PCar) return h * 30;
        else if (v instanceof PBike) return h * 10;
        throw new IllegalArgumentException("unknown vehicle");
    }
    public static void main(String[] args) {
        PVehicle v = new PEv();      // declared type PVehicle, actual type PEv (upcasting)
        System.out.println("Declared type: PVehicle, actual type: " + v.getClass().getSimpleName()
                + ", method run: PEv.rate() -> " + v.rate());
        System.out.println("calculateFee(int)            : " + v.calculateFee(130));
        System.out.println("calculateFee(LocalTime,...)  : " + v.calculateFee(LocalTime.of(9, 0), LocalTime.of(11, 10)));

        PVehicle[] all = { new PBike(), new PCar(), new PEv() };
        for (PVehicle x : all)
            System.out.println(x.getClass().getSimpleName() + " dispatch=" + x.calculateFee(130)
                    + "  instanceof=" + feeByInstanceof(x, 130));

        int reps = 20_000_000; double s = 0;
        for (int i = 0; i < 5_000_000; i++) { s += all[i % 3].calculateFee(130); s += feeByInstanceof(all[i % 3], 130); } // warm-up
        long t0 = System.nanoTime();
        for (int i = 0; i < reps; i++) s += all[i % 3].calculateFee(130);
        long t1 = System.nanoTime();
        for (int i = 0; i < reps; i++) s += feeByInstanceof(all[i % 3], 130);
        long t2 = System.nanoTime();
        System.out.println("20M fee calls: dynamic dispatch " + (t1 - t0) / 1_000_000 + " ms, instanceof chain "
                + (t2 - t1) / 1_000_000 + " ms [" + (s > 0) + "]");
    }
}
