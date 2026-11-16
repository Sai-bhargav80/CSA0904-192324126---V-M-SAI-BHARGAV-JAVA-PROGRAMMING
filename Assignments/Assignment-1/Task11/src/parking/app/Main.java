package parking.app;

import parking.model.ParkingTicket;
import parking.service.MapTicketStore;
import parking.service.ParkingService;

public class Main {
    public static void main(String[] args) {
        Setup.registerStandardTypes();
        ParkingService svc = new ParkingService(new int[]{2, 3}, new MapTicketStore());
        System.out.println("Hand-calculated day (rates: bike 10, car 30, EV 35 per started hour, cap 8 hours):");
        String[][] day = {
            // reg, type, in, out, passHolder
            {"TN01A1", "TWOWHEELER", "540", "600", "false"},   // 60 min  -> 1 h  -> 10
            {"TN02B2", "CAR",        "540", "670", "false"},   // 130 min -> 3 h  -> 90
            {"TN03C3", "EV",         "550", "551", "false"},   // 1 min   -> 1 h  -> 35
            {"TN04D4", "CAR",        "600", "900", "true"},    // pass holder -> 0
            {"TN05E5", "CAR",        "600", "1140", "false"}}; // 540 min -> 9 h -> 270 -> cap 240
        for (String[] d : day) {
            ParkingTicket t = svc.enter(d[0], d[1], Integer.parseInt(d[2]), Boolean.parseBoolean(d[4]));
            System.out.println("IN   " + t);
        }
        for (String[] d : day) {
            double fee = svc.exit(d[0], Integer.parseInt(d[3]));
            System.out.println("OUT  " + d[0] + " fee = Rs " + fee);
        }
        System.out.println("Total collection = Rs " + svc.getTotalCollection() + "   (hand calculation: 10+90+35+0+240 = 375)");
    }
}
