package server.model.cure;

import static org.junit.Assert.*;

import org.junit.Test;

import server.model.cure.CureFactory.CURE_TYPE;

public class TestCureFactory {
	
	@Test
	public void TestCureFactoryCreateCold() {
		CureFactory vf = new CureFactory();
		Cure Cure = vf.createCure(CURE_TYPE.COLDCURE);
		assertEquals(Cure.getType(), CURE_TYPE.COLDCURE);
	}
	
	@Test
	public void TestCureFactoryCreateFlu() {
		CureFactory vf = new CureFactory();
		Cure Cure = vf.createCure(CURE_TYPE.FLUCURE);
		assertEquals(Cure.getType(), CURE_TYPE.FLUCURE);
	}
	
	@Test
	public void TestCureFactoryCreatePox() {
		CureFactory vf = new CureFactory();
		Cure Cure = vf.createCure(CURE_TYPE.POXCURE);
		assertEquals(Cure.getType(), CURE_TYPE.POXCURE);
	}
	
	@Test
	public void TestCureFactoryCreateSARS() {
		CureFactory vf = new CureFactory();
		Cure Cure = vf.createCure(CURE_TYPE.SARSCURE);
		assertEquals(Cure.getType(), CURE_TYPE.SARSCURE);
	}
	
}

