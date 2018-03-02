package server.model.cure;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import server.model.cure.SarsCure;
import server.model.cure.CureFactory.CURE_TYPE;
import server.model.virus.VirusFactory.VIRUS_TYPE;

public class TestSarsCure {
	
	@Test
	public void TestSarsCureGetResearchTime() {
		SarsCure pc = new SarsCure();
		assertEquals(pc.getResearchTime(), 120, 0);
	}
	
	@Test
	public void TestColdCureGetPrice() {
		SarsCure pc = new SarsCure();
		assertEquals(pc.getPrice(), 50000, 0);
	}
	
	@Test
	public void TestColdCureGetCounter() {
		SarsCure pc = new SarsCure();
		assertEquals(pc.getCounterActedVirus(), VIRUS_TYPE.SARS);
	}
	
	@Test
	public void TestColdCureGetType() {
		SarsCure pc = new SarsCure();
		assertEquals(pc.getType(), CURE_TYPE.SARSCURE);
	}
	
}
