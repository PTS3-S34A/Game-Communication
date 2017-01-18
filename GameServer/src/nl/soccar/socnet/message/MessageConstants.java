package nl.soccar.socnet.message;

/**
 *
 * @author PTS34A
 */
public final class MessageConstants {
    
    /**
     * Message ID's.
     */
    public static final int REGISTER_PLAYER_MESSAGE_ID = 1;
    public static final int JOIN_SESSION_MESSAGE_ID = 2;
    public static final int PLAYER_JOINED_SESSION_MESSAGE_ID = 3;
    public static final int PLAYER_LEFT_SESSION_MESSAGE_ID = 4;
    public static final int LEAVE_SESSION_MESSAGE_ID = 5;
    public static final int CHANGE_GAME_STATUS_MESSAGE_ID = 6;
    public static final int PLAYER_CHANGED_GAME_STATUS_MESSAGE_ID = 7;
    public static final int PLAYER_MOVED_MESSAGE_ID = 8;
    public static final int MOVE_PLAYER_MESSAGE_ID = 9;
    public static final int SYNC_POSITION_PLAYER_MESSAGE_ID = 10;
    public static final int CHAT_MESSAGE_ID = 11;
    public static final int SWITCH_TEAM_MESSAGE_ID = 12;
    public static final int SYNC_POSITION_BALL_MESSAGE_ID = 13;
    public static final int SPAWN_CAR_MESSAGE_ID = 14;
    public static final int CHANGE_PLAYER_STATUS_MESSAGE_ID = 15;
    public static final int SPAWN_OBSTACLE_MESSAGE_ID = 16;
    public static final int SPAWN_BALL_MESSAGE_ID = 17;
    public static final int CHANGE_HOST_MESSAGE_ID = 18;
    public static final int EVENT_MESSAGE_ID = 19;
    public static final int STOP_GAME_MESSAGE_ID = 20;
    public static final int GAME_STATUS_MESSAGE_ID = 21;

    private MessageConstants() {
    }
    
}
