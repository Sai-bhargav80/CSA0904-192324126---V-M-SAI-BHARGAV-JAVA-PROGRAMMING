package parking.model;

/** Capability: a vehicle that can use a paid charging slot. */
public interface Chargeable {
    double chargingFee(int kWh);
}
