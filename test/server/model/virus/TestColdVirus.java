package server.model.virus;

import static org.junit.Assert.*;

import org.junit.Test;

import server.model.virus.VirusFactory.VIRUS_TYPE;

public class TestColdVirus {
	
	@Test
	public void TestColdVirusGetResearchTime() {
		ColdVirus cv = new ColdVirus();
		assertEquals(cv.getResearchTime(), 10, 0);
	}
	
	@Test
	public void TestColdVirusGetPrice() {
		ColdVirus cv = new ColdVirus();
		assertEquals(cv.getPrice(), 500, 0);
	}
	
	@Test
	public void TestColdVirusGetDecrementToPopulation() {
		ColdVirus cv = new ColdVirus();
		assertEquals(cv.getDecrementToPopulation(), 15, 0);
	}
	
	@Test
	public void TestColdVirusGetType() {
		ColdVirus cv = new ColdVirus();
		assertEquals(cv.getType(), VIRUS_TYPE.COLD);
	}
}

