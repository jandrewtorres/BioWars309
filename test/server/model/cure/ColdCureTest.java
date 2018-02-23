package server.model.cure;

import static org.junit.Assert.*;

import org.junit.Test;

import server.model.cure.ColdCure;

public class ColdCureTest {
	@Test
	public void TestColdCureGetResearchTime() {
		ColdCure uut = new ColdCure();
		assertEquals(uut.getResearchTime(),10,0);
	}
	@Test
	public void TestColdCureGetPrice() {
		ColdCure uut = new ColdCure();
		assertEquals(uut.getPrice(),500,0);
	}
}

