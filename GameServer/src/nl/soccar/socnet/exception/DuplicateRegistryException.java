package nl.soccar.socnet.exception;

/**
 * An Exception that occurs whenever a Handshake gets sent for the second time on a Session.
 */
public final class DuplicateRegistryException extends Exception {

    /**
     * Initializes this Exception using the given message.
     *
     * @param message The message that explains this Exception.
     */
    public DuplicateRegistryException(String message) {
        super(message);
    }

}
