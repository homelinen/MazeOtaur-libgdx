package uk.calumgilchrist.mazeotaur.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;

import uk.calumgilchrist.mazeotaur.Maze;
import uk.calumgilchrist.mazeotaur.MazeGenerator;
import uk.calumgilchrist.mazeotaur.MazeTemplate;
import uk.calumgilchrist.mazeotaur.ai.AIManager;

public class TestAIManager {

	private MazeTemplate[] mazes;	
	private int mazeHeight;
	private int mazeWidth;
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
	}
	
	/**
	 * Tests a number of different mazes to ensure the pathFinding
	 * algorithm performs correctly
	 */
	@Test
	public void testFindPath() {
		
		Vector2[] start = new Vector2[numberOfTests];
		Vector2[] goal = new Vector2[numberOfTests];
		
		start[0] = new Vector2(0, 9);
		goal[0] = new Vector2(9,0);
		
		start[1] = new Vector2(0, 0);
		goal[1] = new Vector2(8,9);
		
		start[2] = new Vector2(0, 0);
		goal[2] = new Vector2(4,4);
		
		for (int i = 0; i < numberOfTests; i++) {
			LinkedList<Vector2> path = (LinkedList<Vector2>) aiman.findPath(mazes[i], start[i], goal[i]);
			
			assertTrue("Path is not 0", path.size() > 0);
			assertTrue("First node is start", areEqualVectors(start[i], path.getFirst()));
			assertTrue("Last node is goal", areEqualVectors(goal[i], path.getLast()));
			assertTrue("Valid Path", isPath(path));
		}
	}

	public boolean areEqualVectors(Vector2 a, Vector2 b) {
		if (a.x == b.x && a.y == b.y){
			return true;
		} else {
			return false;
		}
	}
		
	/**
	 * Check that each point is adjacent to the next
	 * Ensures a path has no gaps
	 * @param path List of nodes to check
	 * @return Whether or not the path is valid
	 */
	public boolean isPath(List<Vector2> path) {
		
		Iterator<Vector2> nodes = path.iterator();
		Vector2 prevPoint = nodes.next();
		Vector2 curPoint;
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
	public boolean isAdjacent(Vector2 first, Vector2 second) {
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
