package nl.soccar.gamecommuncation.util;

import io.netty.buffer.ByteBuf;

/**
 *
 * @author PTS34A
 */
public final class ByteBufUtilities {

    public static void writeString(String str, ByteBuf buf) {
        byte[] data = str.getBytes();
        int length = data.length;

        buf.writeShort(length);
        buf.writeBytes(data, 0, length);
    }
    
    public static String readString(ByteBuf buf) {
        if (buf.readableBytes() < 2) {
            return null;
        }

        int length = buf.readShort();
        if (buf.readableBytes() < length) {
            buf.resetReaderIndex();
            return null;
        }
        
        byte[] data = new byte[length];
        buf.readBytes(data, 0, length);
        
        return new String(data);
    }

}
