package server.model;

import static org.junit.Assert.*;

import org.junit.Test;

import server.model.ObserverMessage.MESSAGE_TYPE;
import server.model.virus.VirusFactory.VIRUS_TYPE;

public class TestObserverMessage {
	
	@Test
	public void TestObserverMessageTypeUpdateLobby() {
		ObserverMessage om = new ObserverMessage(MESSAGE_TYPE.UPDATE_LOBBY, "Hello");
		assertEquals(om.getMessageType(), MESSAGE_TYPE.UPDATE_LOBBY);
	}
	
	@Test
	public void TestObserverMessageTypeGameUpdate() {
		ObserverMessage om = new ObserverMessage(MESSAGE_TYPE.GAME_UPDATE, "Hello");
		assertEquals(om.getMessageType(), MESSAGE_TYPE.GAME_UPDATE);
	}
	
	@Test
	public void TestObserverMessageTypeGameStarted() {
		ObserverMessage om = new ObserverMessage(MESSAGE_TYPE.GAME_STARTED, "Hello");
		assertEquals(om.getMessageType(), MESSAGE_TYPE.GAME_STARTED);
	}
	
	@Test
	public void TestObserverMessageValue() {
		ObserverMessage om = new ObserverMessage(MESSAGE_TYPE.GAME_UPDATE, "Hello");
		assertEquals(om.getValue(), "Hello");
	}
	
}


