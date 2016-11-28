package nl.soccar.socnet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.soccar.library.Player;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.connection.ConnectionListener;
import nl.soccar.socnet.message.MessageRegistry;

/**
 * A node is the endpoint of an Application. It defines either a client or a
 * server.
 */
public abstract class Node {

    private final MessageRegistry registry = new MessageRegistry();

    private final Map<Player, Connection> connections = new HashMap<>();
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
    
    public final void addConnection(Player player, Connection connection) {
        connections.put(player, connection);
    }
    
    public final void removeConnection(Player player) {
        connections.remove(player);
    }
    
    public final Connection getConnectionFromPlayer(Player player) {
        return connections.get(player);
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
