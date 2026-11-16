import java.lang.ref.WeakReference;

/** Task 5 - static members and object life cycle. */
class Ticket {
    private static int nextNo;                  // shared by ALL tickets of the class
    static final int DAILY_MAX_FEE;
    static { nextNo = 1000; DAILY_MAX_FEE = 300; System.out.println("[static block] Ticket class loaded"); }
    final int no;
    Ticket() { no = ++nextNo; }
    static int issued() { return nextNo - 1000; }   // static method: needs no object
}

/** Alternative: counter as an INSTANCE variable -> every facility restarts at 1001. */
class BadFacility {
    private int counter = 1000;
    int issue() { return ++counter; }
}

/** Fix A: one global counter (static). Fix B: separate counter per facility with a gate prefix. */
class Facility {
    private static int globalCounter = 1000;      // unique across both gates
    private int localCounter = 0;                 // per facility
    private final String gate;
    Facility(String gate) { this.gate = gate; }
    int issueGlobal() { return ++globalCounter; }
    String issueLocal() { return gate + "-" + (++localCounter); }
}

public class Task5Demo {
    static void modify(Ticket t, int x) { x = 99; /* t still the same heap object */ }
    static Ticket make() { return new Ticket(); }

    public static void main(String[] args) throws Exception {
        Ticket t1 = new Ticket(), t2 = new Ticket(), t3 = make();
        System.out.println("Tickets: " + t1.no + ", " + t2.no + ", " + t3.no + "  issued=" + Ticket.issued());

        BadFacility north = new BadFacility(), south = new BadFacility();
        System.out.println("\nInstance counter: north issues " + north.issue() + ", south issues " + south.issue()
                + "  <-- DUPLICATE ticket numbers!");
        Facility n2 = new Facility("N"), s2 = new Facility("S");
        System.out.println("Fix A (static)      : " + n2.issueGlobal() + ", " + s2.issueGlobal() + ", " + n2.issueGlobal());
        System.out.println("Fix B (gate prefix) : " + n2.issueLocal() + ", " + s2.issueLocal() + ", " + n2.issueLocal());

        int x = 5; modify(t1, x);
        System.out.println("\nPrimitive passed by value, x still " + x + "; object reference copied, same Ticket#" + t1.no);

        // life cycle: after the vehicle leaves nothing refers to the ticket -> eligible for GC
        Ticket temp = new Ticket();
        WeakReference<Ticket> w = new WeakReference<>(temp);
        temp = null;                                // vehicle left: last strong reference removed
        System.gc(); Thread.sleep(200);
        System.out.println("After exit, ticket collected by GC? " + (w.get() == null) + " (GC timing is not guaranteed)");
        System.out.println("Space for counter: static = O(1); instance = O(k) for k facilities; tickets O(n) either way.");
    }
}
