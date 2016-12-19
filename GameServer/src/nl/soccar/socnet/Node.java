package nl.soccar.socnet;

import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.connection.ConnectionListener;
import nl.soccar.socnet.message.MessageRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * A node is the endpoint of an Application. It defines either a client or a
 * server.
 */
public abstract class Node {

    private final MessageRegistry registry = new MessageRegistry();

    private final List<ConnectionListener> listeners = new ArrayList<>();

    /**
     * Gets the MessageRegistry bound to this Node, this way connections can
     * determine how Messages should be encoded or decoded.
     *
     * @return The MessageRegistry bound to this Node.
     */
    public MessageRegistry getMessageRegistry() {
        return registry;
    }

    public final void addListener(ConnectionListener listener) {
        listeners.add(listener);
    }

    public final void removeListener(ConnectionListener listener) {
        listeners.remove(listener);
    }

    public final void fireConnected(Connection connection) {
        listeners.forEach(l -> l.onConnected(connection));
    }

    public final void fireDisconnected(Connection connection) {
        listeners.forEach(l -> l.onDisconnected(connection));
    }

}
