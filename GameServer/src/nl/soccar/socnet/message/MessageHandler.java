package nl.soccar.socnet.message;

import io.netty.buffer.ByteBuf;
import nl.soccar.socnet.connection.Connection;


public abstract class MessageHandler<T extends Message> {

    protected abstract void handle(Connection connection, T message) throws Exception;

    protected abstract void encode(Connection connection, T message, ByteBuf buf) throws Exception;

    protected abstract T decode(Connection connection, ByteBuf buf) throws Exception;

}
