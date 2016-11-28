package nl.soccar.socnet.exception;

/**
 * An Exception that occurs whenever a non-registered Message gets received or sent.
 */
public final class InvalidMessageException extends Exception {

    /**
     * Initializes this Exception using the given message.
     *
     * @param message The message that explains this Exception.
     */
    public InvalidMessageException(String message) {
        super(message);
    }

}
