package uk.calumgilchrist.mazeotaur.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import uk.calumgilchrist.mazeotaur.Vecter;

public class VecterTest {

	private Vecter[] vec;
	
	private int x1;
	private int y1;
	
	@Before
	public void setUp() throws Exception {
		int vecCount = 3;
		
		x1 = 20;
		y1 = 10;
		
		vec = new Vecter[vecCount];
		
		vec[0] = new Vecter();
		vec[1] = new Vecter(x1,y1);
		vec[2] = new Vecter(vec[1]);
	}

	/**
	 * Ensure constructors work properly
	 */
	@Test
	public void testVecter() {
		
		assertTrue("X is 0", 0f == vec[0].x);
		assertTrue("Y is 0", 0f == vec[0].y);
		
		assertTrue("X is unchanged", x1 == vec[1].x);
		assertTrue("Y is unchanged", y1 == vec[1].y);
		
		assertTrue("Vecter copied correctly", vec[1].equals(vec[2]));
	}

	@Test
	public void testEqualsVecter() {
		Vecter test[] = new Vecter[7];
		
		int x = 5;
		int y = 6;
		
		test[0] = new Vecter(x, y);
		test[1] = new Vecter(x, y);
		test[2] = new Vecter(x + 1, y);
		test[3] = new Vecter(x, y + 1);
		test[4] = new Vecter(x - 1, y);
		test[5] = new Vecter(x, y - 1);
		test[6] = new Vecter(x + 1, y + 1);
		
		assertTrue("Vector's are the same", test[0].equals(test[1]));
		for (int i = 2; i < test.length; i++) {
			assertFalse("Vector "+ i + "is not same", test[0].equals(test[i]));
		}
	}

	@Test
	public void testCpy() {
		Vecter vecCopy = vec[0].cpy();
		assertTrue("Vecter copied correctly", vec[0].equals(vecCopy));
	}

	@Test
	public void testSetVecter() {
		Vecter newVec = new Vecter(10,10);
		
		assertFalse("Not the same", newVec.equals(vec[0]));
		vec[0].set(newVec);
		assertTrue("Set correctly", newVec.equals(vec[0]));
	}
}
