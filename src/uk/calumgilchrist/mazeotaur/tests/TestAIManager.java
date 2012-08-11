package uk.calumgilchrist.mazeotaur.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.files.FileHandle;

import uk.calumgilchrist.mazeotaur.MazeTemplate;
import uk.calumgilchrist.mazeotaur.Minotaur;
import uk.calumgilchrist.mazeotaur.Vecter;
import uk.calumgilchrist.mazeotaur.ai.AIManager;

public class TestAIManager {

	private MazeTemplate[] mazes;		

	private int mazeWidth;
	private int mazeHeight;

	private int numberOfTests;
	private AIManager aiman;
	
	@Before
	public void setUp() {
		mazeHeight = 10;
		mazeWidth = 10;
		
		numberOfTests = 3;
		mazes = new MazeTemplate[numberOfTests];
		mazes[0] = new MazeTemplate(mazeWidth, mazeHeight);
		mazes[1] = new MazeTemplate(mazeWidth, mazeHeight);
		mazes[2] = new MazeTemplate(mazeWidth, mazeHeight);

		FileHandle file;
		
		for (int i = 0; i < numberOfTests; i++) {
			file = new FileHandle(new File("assets/testmazes/mazetest" + (i + 1) + ".txt"));
			mazes[i].createMaze(file);
		}
		
		aiman = new AIManager();
		assertEquals("No creatures have been added", 0, aiman.getCreatureCount());
	}
	
	/**
	 * Tests a number of different mazes to ensure the pathFinding
	 * algorithm performs correctly
	 */
	@Test
	public void testFindPath() {
		int xMax = mazeWidth - 1;
		int yMax = mazeHeight -1;
		
		int x;
		int y;
		
		Vecter[] start = new Vecter[numberOfTests];
		Vecter[] goal = new Vecter[numberOfTests];
		
		start[0] = new Vecter(0, yMax);
		goal[0] = new Vecter(xMax,0);
		
		start[1] = new Vecter(0, 0);
		
		x = 8;
		goal[1] = new Vecter(x,yMax);
		
		start[2] = new Vecter(0, 0);
		
		x = 4;
		y = 4;
		goal[2] = new Vecter(x, y);
		
		for (int i = 0; i < numberOfTests; i++) {
			LinkedList<Vecter> path = (LinkedList<Vecter>) aiman.findPath(mazes[i], start[i], goal[i]);
			
			assertTrue("Path is not 0", path.size() > 0);
			assertTrue("First node is start", start[i].equals(path.getFirst()));
			assertTrue("Last node is goal", goal[i].equals(path.getLast()));
			assertTrue("Valid Path", isPath(path));
		}
	}

	/**
	 * Add a creature to the manager and update it
	 */
	@Test
	public void testAddCreature() {

		int health = 10;
		Vecter startPos = new Vecter(0,0);
		int patrolLength = 5;
		Vecter endGoal = new Vecter(0, mazeHeight - 1);
		int lineOfSight = 5;
		
		Minotaur min = new Minotaur(health, "Fred", startPos, patrolLength, lineOfSight);
		aiman.addCreature(min, mazes[0], endGoal);
		
		int creatureCount = 1;
		assertEquals("One creatures has been added", creatureCount, aiman.getCreatureCount());
	}
		
	/**
	 * Check that each point is adjacent to the next
	 * Ensures a path has no gaps
	 * @param path List of nodes to check
	 * @return Whether or not the path is valid
	 */
	public boolean isPath(List<Vecter> path) {
		
		Iterator<Vecter> nodes = path.iterator();
		Vecter prevPoint = nodes.next();
		Vecter curPoint;
		while (nodes.hasNext()) {
			curPoint = nodes.next();
			if (!isAdjacent(prevPoint, curPoint)) {
				return false;
			}
			
			prevPoint = curPoint;
		}
		return true;
	}
	
	/**
	 * Check if the Vectors are next to each other
	 * @param first 
	 * @param second
	 * @return
	 */
	public boolean isAdjacent(Vecter first, Vecter second) {
		float ax = first.x;
		float ay = first.y;
		
		float bx = second.x;
		float by = second.y;
		
		int minBelow = -1;
		int maxAbove = 2;
		
		//Check around the points
		for (int chx = minBelow; chx < maxAbove; chx++) {
			for (int chy = minBelow; chy < maxAbove; chy++) {
				if ((ax + chx) == bx && ((ay + chy) == by)) {
					return true;
				}
			}
		}
		
		return false;
	}
}
