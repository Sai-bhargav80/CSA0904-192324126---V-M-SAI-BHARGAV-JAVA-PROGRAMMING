package parking.app;

import parking.model.*;
import parking.service.*;

public class Main12 {
    public static void main(String[] args) {
        ExtendedSetup.registerAll();
        int today = 3;
        ParkingService svc = new ParkingService(new int[]{2, 3, 1, 4, 2}, new MapTicketStore());   // 5 floors, different sizes
        System.out.println("Facility: 5 floors with {2,3,1,4,2} slots, capacity " + svc.getFacility().capacity());
        Pass visitorPass = new OneDayPass(today);
        Pass staffPass = new MonthlyPass(1, 30);
        svc.sellPass(visitorPass);                                   // visitor buys a one-day pass at the gate: Rs 100
        ParkingTicket[] t = {
            svc.enter("EB1", "EBIKE", 540, null, today),             // flat 20
            svc.enter("V1", "VAN", 510, null, today),                // 8:30 -> 10:45 : 30 + 60 + 60 = 150
            svc.enter("C1", "CAR", 540, null, today),                // 130 min -> 90
            svc.enter("C2", "CAR", 600, visitorPass, today),         // free (one-day pass)
            svc.enter("T1", "TWOWHEELER", 600, staffPass, today)};   // free (monthly pass)
        for (ParkingTicket x : t) System.out.println("IN   " + x);
        int[] out = {600, 645, 670, 900, 900};
        String[] regs = {"EB1", "V1", "C1", "C2", "T1"};
        for (int i = 0; i < regs.length; i++) System.out.println("OUT  " + regs[i] + " fee = Rs " + svc.exit(regs[i], out[i]));
        System.out.println("Total collection = Rs " + svc.getTotalCollection() + "   (hand calculation: 100 pass + 20 + 150 + 90 + 0 + 0 = 360)");
    }
}
