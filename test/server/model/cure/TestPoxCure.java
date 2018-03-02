package server.model.cure;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import server.model.cure.PoxCure;
import server.model.cure.CureFactory.CURE_TYPE;
import server.model.virus.VirusFactory.VIRUS_TYPE;

public class TestPoxCure {
	
	@Test
	public void TestPoxCureGetResearchTime() {
		PoxCure pc = new PoxCure();
		assertEquals(pc.getResearchTime(), 60, 0);
	}
	
	@Test
	public void TestColdCureGetPrice() {
		PoxCure pc = new PoxCure();
		assertEquals(pc.getPrice(), 5000, 0);
	}
	
	@Test
	public void TestColdCureGetCounter() {
		PoxCure pc = new PoxCure();
		assertEquals(pc.getCounterActedVirus(), VIRUS_TYPE.POX);
	}
	
	@Test
	public void TestColdCureGetType() {
		PoxCure pc = new PoxCure();
		assertEquals(pc.getType(), CURE_TYPE.POXCURE);
	}
	
}
