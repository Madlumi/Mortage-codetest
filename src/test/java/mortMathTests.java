import static org.junit.Assert.assertEquals;

import org.junit.Test;

import morty.mortMath;

public class mortMathTests {
	// Power function tests
	@Test
	public void testPowInts() {
		assertEquals(1, mortMath.power(10., 0), .0000001);
		assertEquals(10, mortMath.power(10., 1), .0000001);
		assertEquals(100000, mortMath.power(10., 5), .0000001);

	}

	@Test
	public void testPowNegBase() {
		assertEquals(1, mortMath.power(-10., 0), .0000001);
		assertEquals(-10, mortMath.power(-10., 1), .0000001);
		assertEquals(100, mortMath.power(-10., 2), .0000001);

	}

	@Test
	public void testPowZero() {
		assertEquals(1, mortMath.power(0, 0), .0000001);
		assertEquals(0, mortMath.power(0., 1), .0000001);
		assertEquals(0, mortMath.power(0, 5), .0000001);

	}

	@Test
	public void testPowLessThanOne() {
		assertEquals(1, mortMath.power(.5, 0), .0000001);
		assertEquals(.5, mortMath.power(.5, 1), .0000001);
		assertEquals(.03125, mortMath.power(.5, 5), .0000001);
	}

	@Test
	public void testPowNegativeExponent() {
		assertEquals(-1, mortMath.power(1, -1), .0000001);
		assertEquals(-1, mortMath.power(1, -5), .0000001);
	}

	// helper functions
	@Test
	public void TestYearToMontht() {
		assertEquals(60, mortMath.yearToMonth(5));
	}

	@Test
	public void TestPercentageToDecimal() {
		assertEquals(.05, mortMath.percentToDecimal(5), .000001);
	}
}
