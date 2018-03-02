package server.model.virus;

import static org.junit.Assert.*;

import org.junit.Test;

import server.model.virus.VirusFactory.VIRUS_TYPE;

public class TestSARSVirus {
	
	@Test
	public void TestSARSVirusGetResearchTime() {
		SARSVirus cv = new SARSVirus();
		assertEquals(cv.getResearchTime(), 320, 0);
	}
	
	@Test
	public void TestSARSVirusGetPrice() {
		SARSVirus cv = new SARSVirus();
		assertEquals(cv.getPrice(), 50000, 0);
	}
	
	@Test
	public void TestSARSVirusGetDecrementToPopulation() {
		SARSVirus cv = new SARSVirus();
		assertEquals(cv.getDecrementToPopulation(), 1000, 0);
	}
	
	@Test
	public void TestSARSVirusGetType() {
		SARSVirus cv = new SARSVirus();
		assertEquals(cv.getType(), VIRUS_TYPE.SARS);
	}
}

