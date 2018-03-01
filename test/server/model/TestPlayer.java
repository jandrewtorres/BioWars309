package server.model;
import static org.junit.Assert.*;
import org.junit.Test;

import javafx.beans.property.ReadOnlyIntegerProperty;
import server.model.Player;

public class TestPlayer {
	@Test
	public void TestGoldProperty()
	{
		Player p = new Player("TestPlayer");
		ReadOnlyIntegerProperty gold = p.goldProperty();
		assertEquals(gold.intValue(), 500);
	}
	
	@Test
	public void TestPopulationProperty()
	{
		Player p = new Player("TestPlayer");
		ReadOnlyIntegerProperty pop = p.populationProperty();
		assertEquals(pop.intValue(), 10000);
	}
}
