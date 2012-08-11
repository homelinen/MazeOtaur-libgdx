package uk.calumgilchrist.mazeotaur.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.files.FileHandle;

import uk.calumgilchrist.mazeotaur.Enemy;
import uk.calumgilchrist.mazeotaur.MazeTemplate;
import uk.calumgilchrist.mazeotaur.Vecter;

public class EnemyTest {
	private Enemy enemy;
	private List<Vecter> points;
	
	/**
	 * Ensure an enemy class has been initialised for every test
	 */
	@Before
	public void setUp() {
		
		int health = 10;
		int lOS = 10;
		int speed = 5;
		enemy = new Enemy(health, "Hector", new Vecter(0,0), speed, lOS);
		assertEquals("Path is 0", 0, enemy.getPath().size());
		
		points = new LinkedList<Vecter>();
		points.add(new Vecter(0,0));
		
		int yPos = 1;
		points.add(new Vecter(0, yPos));
		
		int noOfElements = 2;
		assertEquals("Two elements were added", noOfElements, points.size());
	}
	
	@Test
	public void testSetPath() {
		enemy.setPath(points);
		assertSame("Original Path is unchanged", points, enemy.getPath());
	}

	@Test
	public void testMoveNode() {
		
		int secsFromMove = 100;
		enemy.setPath(points);
		enemy.setTimeFromLastMove(secsFromMove);
		
		assertTrue("Enemy is at start", new Vecter(0, 0).equals(enemy.getPosition()));
		
		float deltaTime = 0.5f;
		enemy.moveNode(deltaTime);
		assertFalse("Enemy is not at start", new Vecter(0, 0).equals(enemy.getPosition()));
	}
	
	@Test
	public void testIsPlayerInSight() {
		
		int mazeWidth = 10;
		int mazeHeight = 10;
		
		int xMax = mazeWidth - 1;
		int yMax = mazeHeight - 1;
		
		int numTests = 4;
		Vecter[] playerPos = new Vecter[numTests];
		Vecter[] enemyPos = new Vecter[numTests];
		
		playerPos[0] = new Vecter(mazeWidth - 1,0);
		playerPos[1] = new Vecter(mazeWidth - 1,0);
		playerPos[2] = new Vecter(xMax, yMax);
		playerPos[3] = new Vecter(0, yMax);
		
		enemyPos[0] = new Vecter(0, 0);
		enemyPos[1] = new Vecter(xMax, 0);
		enemyPos[2] = new Vecter(0, yMax);
		enemyPos[3] = new Vecter(0, 0);
		
		MazeTemplate maze = new MazeTemplate(mazeWidth, mazeHeight);
		FileHandle file = new FileHandle(new File("assets/testmazes/sightTest.txt"));
		maze.createMaze(file);
		
		for (int i = 0; i < numTests / 2; i++) {
			enemy.setPosition(enemyPos[i]);
			assertTrue("Player is in sight", enemy.isPlayerInSight(playerPos[i], maze));
		}
		
		for (int i = numTests / 2; i < numTests; i++) {
			enemy.setPosition(enemyPos[i]);
			assertFalse("Player is not in sight", enemy.isPlayerInSight(playerPos[i], maze));
		}
	}
}
