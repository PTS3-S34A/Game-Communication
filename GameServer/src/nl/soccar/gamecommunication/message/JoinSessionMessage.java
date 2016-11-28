package nl.soccar.gamecommunication.message;

import nl.soccar.gamecommuncation.CommunicationConstants;
import nl.soccar.gamecommunication.message.handler.JoinSessionMessageHandler;
import nl.soccar.library.enumeration.MapType;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageEvent;

/**
 *
 * @author Lesley
 */
@MessageEvent(id = CommunicationConstants.MESSAGE_ID_JOIN_SESSION, handler = JoinSessionMessageHandler.class)
public final class JoinSessionMessage extends Message {

    private final String name;
    private final String password;

    public JoinSessionMessage(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public int getId() {
        return CommunicationConstants.MESSAGE_ID_JOIN_SESSION;
    }

}
