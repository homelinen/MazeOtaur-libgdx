package uk.calumgilchrist.mazeotaur.tests;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import uk.calumgilchrist.mazeotaur.MazeGenerator;
import uk.calumgilchrist.mazeotaur.Vecter;

public class TestMazeGenerator {

	public MazeGenerator maze;
	
	@Before
	public void setUp() {
		maze = new MazeGenerator(10, 10);
	}

	@Test 
	public void testAddAdjacentWalls() {
		float x = 5;
		float y = 6;
		
		LinkedList<Vecter> walls = new LinkedList<Vecter>();
		assertTrue("Empty List", walls.isEmpty());
		
		walls = (LinkedList<Vecter>) maze.addAdjacentWalls(walls, new Vecter(x,y));
		
		assertFalse("List isn't empty", walls.isEmpty());
		
		boolean test = false;
		for (Vecter wall: walls) {
			test = wall.x == x || wall.y == y;
			assertTrue("Walls are diagonal", test);
		}
	}
}
