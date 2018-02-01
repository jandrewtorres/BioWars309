package server.model;

public class ObserverMessage {
	
	public enum MESSAGE_TYPE {
		UPDATE_LOBBY,
		GAME_UPDATE,
		GAME_STARTED;
	}
	
	private Object value;
	
	private MESSAGE_TYPE type;
	
	public ObserverMessage(MESSAGE_TYPE t, Object v) {
		type = t;
		value = v;
	}
	
	public Object getValue() {
		return value;
	}
	
	public MESSAGE_TYPE getMessageType() {
		return type;
	}
}
