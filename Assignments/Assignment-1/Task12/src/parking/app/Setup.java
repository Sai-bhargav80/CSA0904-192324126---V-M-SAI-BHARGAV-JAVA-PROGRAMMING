package parking.app;

import parking.model.*;

/** Configuration: which vehicle types exist. Adding a type = one more register() line (no edits to core classes). */
public final class Setup {
    private Setup() { }
    public static void registerStandardTypes() {
        VehicleFactory.register("TWOWHEELER", TwoWheeler::new);
        VehicleFactory.register("CAR", Car::new);
        VehicleFactory.register("EV", ElectricCar::new);
    }
}
