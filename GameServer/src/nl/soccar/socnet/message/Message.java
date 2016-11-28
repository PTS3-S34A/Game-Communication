package nl.soccar.socnet.message;

/**
 * A Message if an Object that can be sent to a Session. It is implicitly encoded/decoded and handled by the Network.
 */
public abstract class Message {

    /**
     * The ID that identifies this Message.
     *
     * @return The ID of this Message.
     */
    public abstract int getId();

    @Override
    public String toString() {
        return new StringBuilder(getClass().getSimpleName()).append("[Id=").append(getId()).append("]").toString();
    }

}
