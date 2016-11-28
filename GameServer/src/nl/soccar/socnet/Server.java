package nl.soccar.socnet;

import io.netty.bootstrap.ServerBootstrap;
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
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.group(parentGroup, childGroup);

            bootstrap.childHandler(new NetworkInitializer());
            bootstrap.childAttr(NetworkConstants.ATTRIBUTE_NODE, this);
            bootstrap.bind(address).sync().channel().closeFuture().sync();
        } catch (Exception e) {
            throw new IOException("Failed to bind server.", e);
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }

}
