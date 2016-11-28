package nl.soccar.socnet.exception;

/**
 * An Exception that occurs whenever a Session is not yet registered, and it times out.
 */
public final class SessionTimeoutException extends Exception {

    /**
     * Initializes this Exception using the given message.
     *
     * @param message The message that explains this Exception.
     */
    public SessionTimeoutException(String message) {
        super(message);
    }

}
