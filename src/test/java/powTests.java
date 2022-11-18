import org.junit.Test;

import static org.junit.Assert.*;
public class powTests{
	//Power function tests
	@Test
	public void testPowInts(){
		assertEquals(1,morty.power(10.,0),.0000001);
		assertEquals(10,morty.power(10.,1),.0000001);
		assertEquals(100000,morty.power(10.,5),.0000001);
		
	}
	@Test
	public void testPowNegBase(){
		assertEquals(1,morty.power(-10.,0),.0000001);
		assertEquals(-10,morty.power(-10.,1),.0000001);
		assertEquals(100,morty.power(-10.,2),.0000001);
		
	}
	
	@Test
	public void testPowZero(){
		assertEquals(1,morty.power(0,0),.0000001);
		assertEquals(0,morty.power(0.,1),.0000001);
		assertEquals(0,morty.power(0,5),.0000001);
		
	}
	@Test
	public void testPowLessThanOne(){
		assertEquals(1,morty.power(.5,0),.0000001);
		assertEquals(.5,morty.power(.5,1),.0000001);
		assertEquals(.03125,morty.power(.5,5),.0000001);
	}
	@Test
	public void testPowNegativeExponent(){
		assertEquals(-1,morty.power(1,-1),.0000001);
		assertEquals(-1,morty.power(1,-5),.0000001);
	}
}
