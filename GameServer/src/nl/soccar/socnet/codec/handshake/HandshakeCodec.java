package nl.soccar.socnet.codec.handshake;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

public final class HandshakeCodec extends ByteToMessageCodec<HandshakeResponse> {

    @Override
    protected void encode(ChannelHandlerContext ctx, HandshakeResponse response, ByteBuf buf) throws Exception {
        buf.writeLong(response.getSeed());
        buf.writeInt(response.getNetworkVersion());
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {
        if (buf.readableBytes() < 12) {
            return;
        }

        long seed = buf.readLong();
        int version = buf.readInt();

        list.add(new HandshakeResponse(seed, version));
    }

}
