package server.model;
import static org.junit.Assert.*;
import org.junit.Test;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import server.model.Player;
import server.model.Player.PLAYER_STATUS;
import server.model.cure.CureFactory.CURE_TYPE;
import server.model.virus.VirusFactory.VIRUS_TYPE;

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
	@Test
	public void TestNameProperty()
	{
		Player p = new Player("Randie");
		ReadOnlyStringProperty name = p.nameProperty();		
		assertEquals(name.getValue(), "Randie");
	}
	@Test
	public void TestDefaultStatusProperty()
	{
		Player p = new Player("Randie");
		ReadOnlyObjectProperty<PLAYER_STATUS> status = p.statusProperty();
		assertEquals(status.get(),PLAYER_STATUS.IN_LOBBY);
	}
	
	@Test
	public void BuyCure()
	{
		Player p = new Player("Randie");
		p.buyCure(CURE_TYPE.COLDCURE);
		ReadOnlyIntegerProperty gold = p.goldProperty();
		
		assertEquals(gold.get(), 0);		
	}
	
	@Test
	public void BuyVirus()
	{
		Player p = new Player("Randie");
		p.buyVirus(VIRUS_TYPE.COLD);
		ReadOnlyIntegerProperty gold = p.goldProperty();		
		assertEquals(gold.get(), 0);		
	}
}
