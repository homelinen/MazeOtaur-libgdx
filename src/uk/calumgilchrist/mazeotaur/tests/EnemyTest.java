package uk.calumgilchrist.mazeotaur.tests;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

import uk.calumgilchrist.mazeotaur.Enemy;

public class EnemyTest {
	private Enemy enemy;
	private LinkedList<Vector2> points;
	
	/**
	 * Ensure an enemy class has been initialised for every test
	 */
	@Before
	public void setUp() {
		enemy = new Enemy(10, "Hector", new Vector2(0,0), 5);
		assertEquals("Path is 0", 0, enemy.getPath().size());
		
		points = new LinkedList<Vector2>();
		points.add(new Vector2(0,0));
		points.add(new Vector2(0, 1));
		
		assertEquals("Two elements were added", 2, points.size());
	}
	
	@Test
	public void testSetPath() {
		enemy.setPath(points);
		
		assertSame("Original Path is unchanged", points, enemy.getPath());
	}

	@Test
	public void testMoveNode() {
		enemy.setPath(points);
		
		assertTrue("Enemy is at start", areEqualVectors(new Vector2(0, 0), enemy.getPosition()));
		enemy.moveNode();
		assertFalse("Enemy is not at start", areEqualVectors(new Vector2(0, 0), enemy.getPosition()));
	}

	/**
	 * Determine whether two vectors represent the same co-ords
	 * TODO: Move into a Vector class
	 * @param a Vector 1
	 * @param b Vector 2
	 * @return true if the vectors are the same
	 */
	public boolean areEqualVectors(Vector2 a, Vector2 b) {
		if (a.x == b.x && a.y == b.y){
			return true;
		} else {
			return false;
		}
	}
}
