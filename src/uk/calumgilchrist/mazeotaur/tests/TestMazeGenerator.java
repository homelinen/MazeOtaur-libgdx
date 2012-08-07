package uk.calumgilchrist.mazeotaur.tests;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

import uk.calumgilchrist.mazeotaur.MazeGenerator;

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
		
		LinkedList<Vector2> walls = new LinkedList<Vector2>();
		assertTrue("Empty List", walls.isEmpty());
		
		maze.addAdjacentWalls(walls, new Vector2(x,y));
		
		assertFalse("List isn't empty", walls.isEmpty());
		
		boolean test = false;
		for (Vector2 wall: walls) {
			test = wall.x == x || wall.y == y;
			assertTrue("Walls are diagonal", test);
		}
	}
}
