package parking.model;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/** Registry-based factory: new vehicle types are REGISTERED, this class is never edited. */
public final class VehicleFactory {
    private static final Map<String, Function<String, Vehicle>> REGISTRY = new HashMap<>();
    private VehicleFactory() { }
    public static void register(String type, Function<String, Vehicle> maker) { REGISTRY.put(type.toUpperCase(), maker); }
    public static Vehicle create(String type, String regNo) {
        Function<String, Vehicle> maker = (type == null) ? null : REGISTRY.get(type.trim().toUpperCase());
        if (maker == null) throw new ParkingException("Unknown vehicle type: " + type);
        return maker.apply(regNo);
    }
}
