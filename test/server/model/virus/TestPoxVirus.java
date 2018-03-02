package server.model.virus;

import static org.junit.Assert.*;

import org.junit.Test;

import server.model.virus.VirusFactory.VIRUS_TYPE;

public class TestPoxVirus {
	
	@Test
	public void TestPoxVirusGetResearchTime() {
		PoxVirus cv = new PoxVirus();
		assertEquals(cv.getResearchTime(), 120, 0);
	}
	
	@Test
	public void TestPoxVirusGetPrice() {
		PoxVirus cv = new PoxVirus();
		assertEquals(cv.getPrice(), 5000, 0);
	}
	
	@Test
	public void TestPoxVirusGetDecrementToPopulation() {
		PoxVirus cv = new PoxVirus();
		assertEquals(cv.getDecrementToPopulation(), 375, 0);
	}
	
	@Test
	public void TestPoxVirusGetType() {
		PoxVirus cv = new PoxVirus();
		assertEquals(cv.getType(), VIRUS_TYPE.POX);
	}
}

