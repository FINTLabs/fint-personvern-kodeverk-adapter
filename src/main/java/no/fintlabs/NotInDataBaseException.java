package no.fintlabs;

public class NotInDataBaseException extends RuntimeException {
    public NotInDataBaseException(String message) {
        super(message);
    }
}
