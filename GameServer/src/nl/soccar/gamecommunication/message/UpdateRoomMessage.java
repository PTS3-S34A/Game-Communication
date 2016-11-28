/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.soccar.gamecommunication.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.soccar.gamecommuncation.CommunicationConstants;
import nl.soccar.library.Player;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.socnet.message.Message;

/**
 *
 * @author Lesley
 */
public final class UpdateRoomMessage extends Message {

    private final Map<TeamColour, List<Player>> players = new HashMap<>();
    
    public UpdateRoomMessage(Player... players) {
        
    }
    
    @Override
    public int getId() {
        return CommunicationConstants.MESSAGE_ID_UPDATE_ROOM;
    }
    
}
