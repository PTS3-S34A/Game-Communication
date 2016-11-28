package nl.soccar.socnet;

import nl.soccar.socnet.connection.Connection;
import io.netty.util.AttributeKey;

/**
 * Defines all constants regarding the network.
 */
public final class NetworkConstants {

    /**
     * The attribute that retrieves a Node from a Channel.
     */
    public static final AttributeKey<Node> ATTRIBUTE_NODE = AttributeKey.valueOf("NODE");
    /**
     * The attribute that retrieves a Connection from a Channel.
     */
    public static final AttributeKey<Connection> ATTRIBUTE_SESSION = AttributeKey.valueOf("SESSION");

    /**
     * Defines what the seed of the handshake is. A mismatch in seeds results in disconnection of client/server.
     */
    public static final long HANDSHAKE_SEED = 0x057389932;
    /**
     * Defines on what version the network is running. A mismatch in versions results in disconnection of client/server.
     */
    public static final int NETWORK_VERSION = 0;

    /**
     * Defines after what period a client/server should timeout if a Connection has not been registered yet.
     */
    public static final int TIMEOUT_SECONDS = 10;

    private NetworkConstants() {
    }

}
