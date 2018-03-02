package server.model.virus;

import static org.junit.Assert.*;

import org.junit.Test;

import server.model.virus.VirusFactory.VIRUS_TYPE;

public class TestFluVirus {
	
	@Test
	public void TestFluVirusGetResearchTime() {
		FluVirus cv = new FluVirus();
		assertEquals(cv.getResearchTime(), 60, 0);
	}
	
	@Test
	public void TestFluVirusGetPrice() {
		FluVirus cv = new FluVirus();
		assertEquals(cv.getPrice(), 1000, 0);
	}
	
	@Test
	public void TestFluVirusGetDecrementToPopulation() {
		FluVirus cv = new FluVirus();
		assertEquals(cv.getDecrementToPopulation(), 30, 0);
	}
	
	@Test
	public void TestFluVirusGetType() {
		FluVirus cv = new FluVirus();
		assertEquals(cv.getType(), VIRUS_TYPE.FLU);
	}
}

