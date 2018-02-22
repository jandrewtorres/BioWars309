package test;

import static org.junit.Assert.*;

import org.junit.Test;

import server.model.GameModel;
import server.model.GameModel.GAME_STATUS;
import server.model.Player;

public class GameModelTest {

	@Test
	public void testGameCreate() {
		GameModel model = new GameModel();
		
		// Number of players == 0
		assertTrue(model.getPlayers().size() == 0);
		// Current time == 0
		assertTrue(model.getCurrentTimeProperty().get() == 0);
		// Game status is "Waiting"
		assertTrue(model.getGameStatusProperty().get().equals(GAME_STATUS.WAITING));
	}
	
	@Test 
	public void testAddPlayers() {
		GameModel model = new GameModel();
		Player pOne = new Player("John");
		Player pTwo = new Player("Torres");
		model.addPlayer(pOne);
		model.addPlayer(pTwo);
		
		assertTrue(model.getPlayers().size() == 2);
	}
	
	@Test
	public void testGameStart() {
		GameModel model = new GameModel();
		Player pOne = new Player("John");
		Player pTwo = new Player("Torres");
		model.addPlayer(pOne);
		model.addPlayer(pTwo);
		
		model.startGame();
		
		assertTrue(model.getGameStatusProperty().get().equals(GAME_STATUS.IN_PROGRESS));
	}
	
	@Test
	public void testGameTick() {
		GameModel model = new GameModel();
		Player pOne = new Player("John");
		Player pTwo = new Player("Torres");
		model.addPlayer(pOne);
		model.addPlayer(pTwo);
		
		model.tick();
		
		assertTrue(pOne.goldProperty().get() == 510);
		assertTrue(pOne.populationProperty().get() == 10005);
	}

}
