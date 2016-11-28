/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.soccar.gamecommunication.message.handler;

import io.netty.buffer.ByteBuf;
import nl.soccar.gamecommuncation.CommunicationConstants;
import nl.soccar.gamecommuncation.util.ByteBufUtilities;
import nl.soccar.gamecommunication.message.JoinSessionMessage;
import nl.soccar.library.Player;
import nl.soccar.library.Session;
import nl.soccar.library.SessionController;
import nl.soccar.library.enumeration.MapType;
import nl.soccar.socnet.connection.Connection;
import nl.soccar.socnet.message.MessageHandler;

/**
 *
 * @author PTS34A
 */
public final class JoinSessionMessageHandler extends MessageHandler<JoinSessionMessage> {

    @Override
    protected void handle(Connection connection, JoinSessionMessage message) throws Exception {
        Player player = connection.getPlayer();
        
        Session session = SessionController.getInstance().getAllSessions().stream().filter(s -> s.getRoom().getName().equals(message.getName())).findFirst().get();
        SessionController.getInstance().join(session, message.getPassword(), player);
    }

    @Override
    protected void encode(Connection connection, JoinSessionMessage message, ByteBuf buf) throws Exception {
        ByteBufUtilities.writeString(message.getName(), buf);
        ByteBufUtilities.writeString(message.getPassword(), buf);
    }

    @Override
    protected JoinSessionMessage decode(Connection connection, ByteBuf buf) throws Exception {
        String name = ByteBufUtilities.readString(buf);
        String password = ByteBufUtilities.readString(buf);
        
        return new JoinSessionMessage(name, password);
    }

}
