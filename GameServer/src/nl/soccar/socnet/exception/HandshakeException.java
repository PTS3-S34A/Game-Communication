package nl.soccar.socnet.exception;

/**
 * An Exception that occurs when handshake values mismatch.
 */
public final class HandshakeException extends Exception {

    /**
     * Initializes this Exception using the given message.
     *
     * @param message The message that explains this Exception.
     */
    public HandshakeException(String message) {
        super(message);
    }

}
