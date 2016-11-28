package nl.soccar.gamecommunication.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gamecommuncation.util.ByteBufUtilities;
import nl.soccar.gamecommunication.message.PlayerConnectedMessage;
import nl.soccar.library.Player;
import nl.soccar.library.enumeration.CarType;
import nl.soccar.library.enumeration.Privilege;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 *
 * @author PTS34A
 */
public final class PlayerConnectedMessageHandler extends MessageHandler<PlayerConnectedMessage> {

    @Override
    protected void handle(Connection connection, PlayerConnectedMessage message) throws Exception {
        Player player = new Player(message.getName(), Privilege.NORMAL, message.getCarType());
        connection.setPlayer(player);
    }

    @Override
    protected void encode(Connection connection, PlayerConnectedMessage message, ByteBuf buf) throws Exception {
        ByteBufUtilities.writeString(message.getName(), buf);
        ByteBufUtilities.writeString(message.getCarType().name(), buf);
    }

    @Override
    protected PlayerConnectedMessage decode(Connection connection, ByteBuf buf) throws Exception {
        String name = ByteBufUtilities.readString(buf);
        CarType type = CarType.valueOf(ByteBufUtilities.readString(buf));
        
        return new PlayerConnectedMessage(name, type);
    }
    
}
