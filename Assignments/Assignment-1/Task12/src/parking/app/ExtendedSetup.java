package parking.app;

import parking.model.*;

/** Task 12 configuration: the two new types are just two more register() lines. */
public final class ExtendedSetup {
    private ExtendedSetup() { }
    public static void registerAll() {
        Setup.registerStandardTypes();
        VehicleFactory.register("EBIKE", EBike::new);
        VehicleFactory.register("VAN", DeliveryVan::new);
    }
}
