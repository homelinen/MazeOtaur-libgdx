package uk.calumgilchrist.mazeotaur.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

import uk.calumgilchrist.mazeotaur.Player;

public class TestPlayer {

	private Player player;
	
	private Vector2 startPos;
	private String name;
	private int health;
	
	@Before
	public void setUp() {
		health = 10;
		startPos = new Vector2(0,0);
		name = "Player1";
		
		player = new Player(health, name, startPos);
		
		player.setTimeFromLastMove(1000);
	}

	@Test
	public void testPlayer() {
		assertEquals("Initial Health is Unchanged", health, player.getHealth());
		assertSame("Name is as Started", name, player.getName());
		assertSame("Player position is same as start position", startPos, player.getPosition());
	}

	@Test
	public void testMove() {
		float deltaMult = 0;
		float speedConst = 0.12f;
		
		assertSame(startPos, player.getPosition());
		
		setUp();
		player.move(deltaMult);
		assertSame("Player has not moved", startPos, player.getPosition());
		
		setUp();
		player.move(speedConst);
		assertSame("Player has not moved", startPos, player.getPosition());
		
		setUp();
		preTest();
		
		deltaMult = 1f;
		player.move(deltaMult);

		assertNotSame("Player has moved", startPos, player.getPosition());
		
		assertEquals("ChangeX has been reset", 0, player.getChangeX());
		assertEquals("ChangeY has been reset", 0, player.getChangeY());
		
	}

	@Test
	public void testFindNextPos() {
		assertSame(startPos, player.getPosition());
		
		preTest();
		
		assertNotSame(startPos, player.findNextPos());
	}
	
	@Test
	public void testSetChangeX() {
		int x0 = 0;
		int x1 = 1;
		int x2 = -1;
		int x3 = 10;
		int x4 = -10;
		
		assertEquals(0, player.getChangeX());
		
		player.setChangeX(x0);
		assertEquals(x0, player.getChangeX());
		
		player.setChangeX(x1);
		assertEquals(x1, player.getChangeX());
		
		player.setChangeX(x2);
		assertEquals(x2, player.getChangeX());
		
		player.setChangeX(x3);
		assertEquals("Change X is equal to 1, 0 or -1", x1, player.getChangeX());
		
		player.setChangeX(x4);
		assertEquals("Change X is equal to 1, 0 or -1", x2, player.getChangeX());
	}
	
	//Almost identical to testSetChangeY
	@Test 
	public void testSetChangeY() {
		int y0 = 0;
		int y1 = 1;
		int y2 = -1;
		int y3 = 10;
		int y4 = -10;
		
		assertEquals(0, player.getChangeY());
		
		player.setChangeY(y0);
		assertEquals(y0, player.getChangeY());
		
		player.setChangeY(y1);
		assertEquals(y1, player.getChangeY());
		
		player.setChangeY(y2);
		assertEquals(y2, player.getChangeY());
		
		player.setChangeY(y3);
		assertEquals("Change Y is equal to 1, 0 or -1", y1, player.getChangeY());
		
		player.setChangeY(y4);
		assertEquals("Change Y is equal to 1, 0 or -1", y2, player.getChangeY());
		
	}
	
	public void preTest() {
		int deltaMove = 1;
		
		player.setChangeX(deltaMove);
		assertEquals(deltaMove, player.getChangeX());
		
		player.setChangeY(deltaMove);
		assertEquals(deltaMove, player.getChangeY());
	}
}
