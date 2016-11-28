package nl.soccar.socnet.exception;

/**
 * Occurs whenever a Session is not (yet) registered when a Message gets sent or received.
 */
public final class NotRegisteredException extends Exception {

    /**
     * Initializes this Exception using the given message.
     *
     * @param message The message that explains this Exception.
     */
    public NotRegisteredException(String message) {
        super(message);
    }

}
