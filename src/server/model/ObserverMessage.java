package server.model;

public class ObserverMessage {
	
	public static enum MESSAGE_TYPE {
		UPDATE_LOBBY,
		TICK,
		GAME_STARTED;
	}
	
	public Object value;
	
	public MESSAGE_TYPE type;
	
	public ObserverMessage(MESSAGE_TYPE t, Object v) {
		type = t;
		value = v;
	}
}
