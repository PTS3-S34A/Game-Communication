package nl.soccar.socnet.codec.message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import nl.soccar.socnet.NetworkConstants;
import nl.soccar.socnet.Node;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageRegistry;
import nl.soccar.socnet.connection.Connection;

import java.util.List;

/**
 * Provides ways to encode and decode a Message.
 */
public final class MessageCodec extends ByteToMessageCodec<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message message, ByteBuf buf) throws Exception {
        Channel channel = ctx.channel();
        Connection session = channel.attr(NetworkConstants.ATTRIBUTE_SESSION).get();

        Node node = channel.attr(NetworkConstants.ATTRIBUTE_NODE).get();
        MessageRegistry registry = node.getMessageRegistry();

        buf.writeByte(message.getId());
        registry.encode(session, message, buf);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {
        if (buf.readableBytes() < 1) {
            return;
        }

        Channel channel = ctx.channel();
        Connection session = channel.attr(NetworkConstants.ATTRIBUTE_SESSION).get();

        Node node = channel.attr(NetworkConstants.ATTRIBUTE_NODE).get();
        MessageRegistry registry = node.getMessageRegistry();

        int id = buf.readByte();

        Message message = registry.decode(id, session, buf);
        if (message == null) {
            return;
        }

        list.add(message);
    }

}
