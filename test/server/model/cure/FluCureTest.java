package server.model.cure;

i
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import server.model.cure.FluCure;

public class FluCureTest {
	@Test
	public void TestFluCureGetResearchTime() {
		FluCure uut = new FluCure();
		assertEquals(uut.getResearchTime(),30,0);
	}
	@Test
	public void TestColdCureGetPrice() {
		FluCure uut = new FluCure();
		assertEquals(uut.getPrice(),1000,0);
	}
}
