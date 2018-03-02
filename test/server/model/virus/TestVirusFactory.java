package server.model.virus;

import static org.junit.Assert.*;

import org.junit.Test;

import server.model.virus.VirusFactory.VIRUS_TYPE;

public class TestVirusFactory {
	
	@Test
	public void TestVirusFactoryCreateCold() {
		VirusFactory vf = new VirusFactory();
		Virus virus = vf.createVirus(VIRUS_TYPE.COLD);
		assertEquals(virus.getType(), VIRUS_TYPE.COLD);
	}
	
	@Test
	public void TestVirusFactoryCreateFlu() {
		VirusFactory vf = new VirusFactory();
		Virus virus = vf.createVirus(VIRUS_TYPE.FLU);
		assertEquals(virus.getType(), VIRUS_TYPE.FLU);
	}
	
	@Test
	public void TestVirusFactoryCreatePox() {
		VirusFactory vf = new VirusFactory();
		Virus virus = vf.createVirus(VIRUS_TYPE.POX);
		assertEquals(virus.getType(), VIRUS_TYPE.POX);
	}
	
	@Test
	public void TestVirusFactoryCreateSARS() {
		VirusFactory vf = new VirusFactory();
		Virus virus = vf.createVirus(VIRUS_TYPE.SARS);
		assertEquals(virus.getType(), VIRUS_TYPE.SARS);
	}
	
}

