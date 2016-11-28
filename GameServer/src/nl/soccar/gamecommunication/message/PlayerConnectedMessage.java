package nl.soccar.gamecommunication.message;

import nl.soccar.gamecommuncation.CommunicationConstants;
import nl.soccar.gamecommunication.message.handler.PlayerConnectedMessageHandler;
import nl.soccar.library.enumeration.CarType;
import nl.soccar.socnet.message.Message;
import nl.soccar.socnet.message.MessageEvent;

/**
 *
 * @author PTS34A
 */
@MessageEvent(id = CommunicationConstants.MESSAGE_ID_PLAYER_JOINED, handler = PlayerConnectedMessageHandler.class)
public final class PlayerConnectedMessage extends Message {

    private final String name;
    private final CarType type;
    
    public PlayerConnectedMessage(String name, CarType type) {
        this.name = name;
        this.type = type;
    }
    
    public String getName() {
        return name;
    }
    
    public CarType getCarType() {
        return type;
    }
    
    @Override
    public int getId() {
        return CommunicationConstants.MESSAGE_ID_PLAYER_JOINED;
    }
    
}
