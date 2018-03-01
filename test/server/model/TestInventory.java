package server.model;
import static org.junit.Assert.*;
import org.junit.Test;

import javafx.beans.property.ReadOnlyIntegerProperty;
import server.model.Inventory;


public class TestInventory {
	@Test
	public void TestGetColdVirusCount()
	{
		Inventory inv = new Inventory();
		ReadOnlyIntegerProperty colds = inv.getColdVirusCount();
		assertEquals(colds.intValue(), 0);
	}
	
	@Test
	public void TestGetFluVirusCount()
	{
		Inventory inv = new Inventory();
		ReadOnlyIntegerProperty flus = inv.getFluVirusCount();
		assertEquals(flus.intValue(), 0);
	}
}
