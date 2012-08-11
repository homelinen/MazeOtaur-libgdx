package uk.calumgilchrist.mazeotaur.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.files.FileHandle;

import uk.calumgilchrist.mazeotaur.Enemy;
import uk.calumgilchrist.mazeotaur.MazeTemplate;
import uk.calumgilchrist.mazeotaur.Vecter;

public class EnemyTest {
	private Enemy enemy;
	private LinkedList<Vecter> points;
	
	/**
	 * Ensure an enemy class has been initialised for every test
	 */
	@Before
	public void setUp() {
		
		enemy = new Enemy(10, "Hector", new Vecter(0,0), 5, 10);
		assertEquals("Path is 0", 0, enemy.getPath().size());
		
		points = new LinkedList<Vecter>();
		points.add(new Vecter(0,0));
		points.add(new Vecter(0, 1));
		
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
		enemy.setTimeFromLastMove(100);
		
		assertTrue("Enemy is at start", new Vecter(0, 0).equals(enemy.getPosition()));
		enemy.moveNode(0.5f);
		assertFalse("Enemy is not at start", new Vecter(0, 0).equals(enemy.getPosition()));
	}
	
	@Test
	public void testIsPlayerInSight() {
		Vecter playPos = new Vecter(9,0);
		
		MazeTemplate maze = new MazeTemplate(10, 10);
		FileHandle file = new FileHandle(new File("assets/testmazes/sightTest.txt"));
		maze.createMaze(file);
		System.out.println(maze.printMaze());
		
		enemy.setPosition(new Vecter(0,0));
		assertTrue("Player is in sight", enemy.isPlayerInSight(playPos, maze));
		
		enemy.setPosition(new Vecter(9,0));
		playPos.set(9, 9);
		assertTrue("Player is in sight", enemy.isPlayerInSight(playPos, maze));
		
		enemy.setPosition(new Vecter(0,9));
		playPos.set(9,9);
		assertFalse("Player is not in sight", enemy.isPlayerInSight(playPos, maze));
		
		enemy.setPosition(new Vecter(0,0));
		playPos.set(0, 9);
		assertFalse("Player is not in sight", enemy.isPlayerInSight(playPos, maze));
	}
}
