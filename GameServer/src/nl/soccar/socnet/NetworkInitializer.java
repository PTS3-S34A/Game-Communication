package nl.soccar.socnet;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import nl.soccar.socnet.codec.handshake.HandshakeCodec;

/**
 * Initializes a (Netty) Channel by adding the correct handlers. In its initial state, it provides a way to decode/encode handshakes, handle handshakes and handle timeouts.
 */
final class NetworkInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("timeout", new IdleStateHandler(NetworkConstants.TIMEOUT_SECONDS, NetworkConstants.TIMEOUT_SECONDS, NetworkConstants.TIMEOUT_SECONDS));
        pipeline.addLast("codec", new HandshakeCodec());
        pipeline.addLast("handler", new NetworkHandler());
    }

}
