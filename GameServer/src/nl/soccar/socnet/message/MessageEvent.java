package nl.soccar.socnet.message;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A MessageEvent is an annotation that defines what ID a Message has, and what Handler should be used to encode/decode and handle it.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MessageEvent {

    /**
     * The ID of a Message.
     *
     * @return The ID of a Message.
     */
    int id();

    /**
     * That class that belongs to the Handler of a Message.
     *
     * @return The Handler class of a Message.
     */
    Class<? extends MessageHandler<?>> handler();

}
