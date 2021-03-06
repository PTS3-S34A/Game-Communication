package nl.soccar.socnet;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * A Client is a node that initializes the network in such a way that a Session
 * connects to a Server and is able to send Messages.
 */
public final class Client extends Node {

    private EventLoopGroup group;
    private Channel channel;

    /**
     * Connects this Client to the specified host and port.
     *
     * @param host The host to connect this Client to.
     * @param port The port to connect this Client to.
     * @throws IOException When connecting this Client fails.
     */
    public void connect(String host, int port) throws IOException {
        connect(new InetSocketAddress(host, port));
    }

    /**
     * Connects this Client to the specified address.
     *
     * @param address The address to connect this Client to.
     * @throws IOException When connecting this Client fails.
     */
    public void connect(SocketAddress address) throws IOException {
        group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(group);

            bootstrap.handler(new NetworkInitializer());
            bootstrap.attr(NetworkConstants.ATTRIBUTE_NODE, this);
            channel = bootstrap.connect(address).channel();
        } catch (Exception e) {
            throw new IOException("Failed to conect client.", e);
        }
    }

    public void disconnect() {
        if (channel == null) {
            return;
        }

        group.shutdownGracefully();
        channel.close().addListener(l -> {
            channel = null;
        });
    }

}
