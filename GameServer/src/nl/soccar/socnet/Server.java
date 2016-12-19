package nl.soccar.socnet;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * A Server is a Node that provides a way initialize the network in such a way that Sessions get accepted, and distributed.
 */
public final class Server extends Node {

    private EventLoopGroup parentGroup;
    private EventLoopGroup childGroup;
    private Channel channel;

    /**
     * Binds this Server on the specified port.
     *
     * @param port The port to bind this Server on.
     * @throws IOException When binding this Server fails.
     */
    public void bind(int port) throws IOException {
        bind(new InetSocketAddress(port));
    }

    /**
     * Binds this Server on the specified address.
     *
     * @param address The address to bind this Server on.
     * @throws IOException When binding this Server fails.
     */
    public void bind(SocketAddress address) throws IOException {
        parentGroup = new NioEventLoopGroup();
        childGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.group(parentGroup, childGroup);

            bootstrap.childHandler(new NetworkInitializer());
            bootstrap.childAttr(NetworkConstants.ATTRIBUTE_NODE, this);
            channel = bootstrap.bind(address).channel();
        } catch (Exception e) {
            throw new IOException("Failed to bind server.", e);
        }
    }

    public void close() {
        if (channel == null) {
            return;
        }

        parentGroup.shutdownGracefully();
        childGroup.shutdownGracefully();

        channel.close().addListener(l -> channel = null);
    }

}
