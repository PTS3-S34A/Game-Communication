package nl.soccar.socnet.connection;

/**
 *
 * @author PTS34A
 */
public interface ConnectionListener {
    
    void onConnected(Connection connection);
    
    void onDisconnected(Connection connection);
    
}
