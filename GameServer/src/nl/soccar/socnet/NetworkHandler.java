package nl.soccar.socnet;

import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.exception.InvalidMessageException;
import nl.soccar.socnet.exception.SessionTimeoutException;
import nl.soccar.socnet.exception.HandshakeException;
import nl.soccar.socnet.exception.NotRegisteredException;
import nl.soccar.socnet.exception.DuplicateRegistryException;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import nl.soccar.socnet.codec.handshake.HandshakeResponse;
import nl.soccar.socnet.codec.message.MessageCodec;
import nl.soccar.socnet.message.Message;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A (Netty) Handler that handles certain states of Channels. Namely it sends a handshake on connection, and handles Messages sent by connections.
 */
final class NetworkHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = Logger.getLogger(NetworkHandler.class.getSimpleName());

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channel.writeAndFlush(new HandshakeResponse(NetworkConstants.HANDSHAKE_SEED, NetworkConstants.NETWORK_VERSION));

        LOGGER.log(Level.INFO, "Channel {0} connected.", channel);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        
        Node node = channel.attr(NetworkConstants.ATTRIBUTE_NODE).getAndSet(null);
        Connection connection = channel.attr(NetworkConstants.ATTRIBUTE_SESSION).getAndSet(null);
        
        if (node != null && connection != null) {
            node.fireDisconnected(connection);
        }

        LOGGER.log(Level.INFO, "Channel {0} disconnected.", channel);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object o) throws Exception {
        Channel channel = ctx.channel();

        if (o instanceof HandshakeResponse) {
            if (channel.hasAttr(NetworkConstants.ATTRIBUTE_SESSION)) {
                throw new DuplicateRegistryException(String.format("Channel (%s) already registered.", channel));
            }

            HandshakeResponse response = (HandshakeResponse) o;
            long seed = response.getSeed();
            int version = response.getNetworkVersion();

            if (seed != NetworkConstants.HANDSHAKE_SEED) {
                throw new HandshakeException(String.format("Handshake seed mismatch for channel (%s) (%d != %d).", channel, seed, NetworkConstants.HANDSHAKE_SEED));
            }

            if (version != NetworkConstants.NETWORK_VERSION) {
                throw new HandshakeException(String.format("Network version mismatch for channel (%s) (%d != %d).", channel, version, NetworkConstants.NETWORK_VERSION));
            }

            Connection connection = new Connection(channel);

            channel.attr(NetworkConstants.ATTRIBUTE_SESSION).set(connection);
            channel.pipeline().replace("codec", "codec", new MessageCodec());
            
            channel.attr(NetworkConstants.ATTRIBUTE_NODE).get().fireConnected(connection);

            LOGGER.log(Level.INFO, "Channel ({0}) registered.", channel);
        } else if (o instanceof Message) {
            Node node = channel.attr(NetworkConstants.ATTRIBUTE_NODE).get();
            Connection session = channel.attr(NetworkConstants.ATTRIBUTE_SESSION).get();
            if (session == null) {
                throw new NotRegisteredException(String.format("Channel (%s) not registered when receiving message (%s).", channel, o));
            }

            Message message = (Message) o;
            node.getMessageRegistry().handle(session, message);

            LOGGER.log(Level.INFO, "Channel ({0}) received message ({1}).", new Object[] { session.getRemoteAddress(), message });
        } else {
            throw new InvalidMessageException(String.format("Invalid message (%s) for channel (%s).", o.getClass().getSimpleName(), channel));
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {
        Channel channel = ctx.channel();

        if (event instanceof IdleStateEvent && !channel.hasAttr(NetworkConstants.ATTRIBUTE_SESSION)) {
            throw new SessionTimeoutException(String.format("Channel %s timed out (didn't send handshake in time).", channel));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();

        if (cause.getMessage().contains("De externe host heeft een verbinding verbroken")) {
            return;
        }

        LOGGER.log(Level.INFO, "An exception occurred on a channel.", cause);
    }

}
