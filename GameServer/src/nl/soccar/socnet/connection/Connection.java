package nl.soccar.socnet.connection;

import io.netty.channel.Channel;
import nl.soccar.socnet.message.Message;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import nl.soccar.library.Player;
import nl.soccar.socnet.NetworkConstants;
import nl.soccar.socnet.Node;

/**
 * A Connection is a connection between two nodes (Client and Server). It
 * provides a way to send Messages and request certain properties.
 */
public final class Connection {

    private final Channel channel;
    private Player player;

    /**
     * Initializes a new Session using the given (Netty) Channel.
     *
     * @param channel The underlying Channel that this Session should use to
     * send Messages on.
     */
    public Connection(Channel channel) {
        this.channel = channel;
    }

    /**
     * Sends a Message to the other Node.
     *
     * @param message The message to send.
     */
    public void send(Message message) {
        channel.writeAndFlush(message);
    }

    /**
     * Closes the underlying connection of this Connection.
     */
    public void close() {
        channel.close();
    }

    /**
     * Gets the (local) Node to which this Connection is connected.
     *
     * @return The Node to which this Connection is connected.
     */
    public Node getNode() {
        return channel.attr(NetworkConstants.ATTRIBUTE_NODE).get();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets the local address of this Connection.
     *
     * @return The local address of this Connection.
     */
    public InetSocketAddress getLocalAddress() {
        return (InetSocketAddress) channel.localAddress();
    }

    /**
     * Gets the remote address of this Connection.
     *
     * @return The remote address of this Connection.
     */
    public InetSocketAddress getRemoteAddress() {
        return (InetSocketAddress) channel.remoteAddress();
    }

}
