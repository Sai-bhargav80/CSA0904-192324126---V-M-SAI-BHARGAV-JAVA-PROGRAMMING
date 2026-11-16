package parking.model;

/** Business-rule violation (full, duplicate, unknown type, invalid exit ...). */
public class ParkingException extends RuntimeException {
    public ParkingException(String message) { super(message); }
}
