package uk.calumgilchrist.mazeotaur.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

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

	private MazeTemplate maze1;
	private MazeTemplate maze2;
	private MazeTemplate maze3;
	
	private int mazeHeight;
	private int mazeWidth;
	
	private AIManager aiman;
	
	@Before
	public void setUp() {
		mazeHeight = 10;
		mazeWidth = 10;
		
		maze1 = new MazeTemplate(mazeWidth, mazeHeight);
		maze2 = new MazeTemplate(mazeWidth, mazeHeight);
		maze3 = new MazeTemplate(mazeWidth, mazeHeight);

		FileHandle file;
		
		file = new FileHandle(new File("assets/testmazes/mazetest1.txt"));
		maze1.createMaze(file);
		
		file = new FileHandle(new File("assets/testmazes/mazetest2.txt"));
		maze2.createMaze(file);
		
		file = new FileHandle(new File("assets/testmazes/mazetest3.txt"));
		maze3.createMaze(file);

		
		aiman = new AIManager();
	}
	
	@Test
	public void testFindPath() {
		
		int sx = 0;
		int sy = 9;
		
		int gx = 9;
		int gy = 0;
		
		Vector2 start = new Vector2(sx, sy);
		Vector2 goal = new Vector2(gx,gy);
		
		System.out.println("Goal: " + goal.toString());
		LinkedList<Vector2> path = (LinkedList<Vector2>) aiman.findPath(maze1, start, goal);
		
		MazeTemplate pathMaze = new MazeTemplate(mazeWidth, mazeHeight);
		pathMaze.createMaze(path);
		System.out.println(pathMaze.printMaze());
		
		assertTrue("Path is not 0", path.size() > 0);
		assertTrue("First node is start", areEqualVectors(start, path.getFirst()));
		assertTrue("Last node is goal", areEqualVectors(goal, path.getLast()));
	}

	public boolean areEqualVectors(Vector2 a, Vector2 b) {
		if (a.x == b.x && a.y == b.y){
			return true;
		} else {
			return false;
		}
	}
}
