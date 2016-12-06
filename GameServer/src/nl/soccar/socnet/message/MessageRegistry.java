package nl.soccar.socnet.message;

import io.netty.buffer.ByteBuf;
import nl.soccar.socnet.connection.Connection;

import java.util.HashMap;
import java.util.Map;

/**
 * A MessageRegistry provides ways to add MessageHandlers to this registry. This way, Messages can be encoded/decoded and handled using Message IDs only.
 */
public final class MessageRegistry {

    private final Map<Integer, MessageHandler<?>> handlers = new HashMap<>();

    /**
     * Registers a Message on this MessageRegistry. Via the annotation provided in the Message class this Registry will determine what handler and ID to use.
     *
     * @param clazz The class of the Message to register.
     */
    public void register(Class<? extends Message> clazz) {
        if (!clazz.isAnnotationPresent(MessageEvent.class)) {
            return;
        }

        try {
            MessageEvent event = clazz.getAnnotation(MessageEvent.class);

            int id = event.id();
            Class<?> handlerClazz = event.handler();

            MessageHandler<?> handler = (MessageHandler<?>) handlerClazz.newInstance();
            handlers.put(id, handler);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Encodes a Message into a (Netty) ByteBuf. If the specified Message is not yet registered, this method will not do anything.
     *
     * @param session The Connection on which the Message will be sent.
     * @param message The Message to be encoded.
     * @param buf The buffer into which the Message will be encoded.
     * @throws Exception If encoding the specified Message fails.
     */
    public void encode(Connection session, Message message, ByteBuf buf) throws Exception {
        MessageHandler<Message> handler = (MessageHandler<Message>) handlers.get(message.getId());
        if (handler == null) {
            return;
        }

        handler.encode(session, message, buf);
    }

    /**
     * Decodes a buffer into a Message. If the Message to be decoded in not yet registered, this method will not do anything.
     *
     * @param id The ID of the Message that will be decoded.
     * @param session The Connection on which the Message was sent.
     * @param buf The buffer into which the Message was encoded.
     * @return A decoded Message.
     * @throws Exception If decoding the Message fails.
     */
    public Message decode(int id, Connection session, ByteBuf buf) throws Exception {
        MessageHandler<Message> handler = (MessageHandler<Message>) handlers.get(id);
        if (handler == null) {
            return null;
        }

        return handler.decode(session, buf);
    }

    /**
     * Handles a Message sent by a Connection.
     *
     * @param session The Connection on which the Message was sent.
     * @param message The Message that was sent.
     * @throws Exception If handling the Message fails.
     */
    public void handle(Connection session, Message message) throws Exception {
        MessageHandler<Message> handler = (MessageHandler<Message>) handlers.get(message.getId());
        if (handler == null) {
            return;
        }
  
        handler.handle(session, message);
    }

}
