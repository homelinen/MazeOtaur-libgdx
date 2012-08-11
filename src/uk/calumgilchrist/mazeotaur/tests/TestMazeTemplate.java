package uk.calumgilchrist.mazeotaur.tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.files.FileHandle;

import uk.calumgilchrist.mazeotaur.MazeTemplate;
import uk.calumgilchrist.mazeotaur.Vecter;

public class TestMazeTemplate {

	private MazeTemplate maze;
	private int width;
	private int height;
	
	@Before
	public void setUp() {
		width = 10;
		height = 10;
		
		FileHandle file = new FileHandle(new File("assets/testmazes/sightTest.txt"));
		
		maze = new MazeTemplate(width, height);
		maze.createMaze(file);
	}
	
	@Test
	public void testMazeTemplate() {
		assertEquals(width, maze.getWidth());
		assertEquals(height, maze.getHeight());
	}
	
	/**
	 * TODO: Check each line of file and current maze match
	 */
	@Test
	public void testCreateMaze() {
		
	}

	@Test
	public void testGetCellVecter() {
		int mazeXLim = width - 1;
		int mazeYLim = height - 1;
		
		//Border
		Vecter cellTopLeft= new Vecter(0,0);
		Vecter cellBottomRight = new Vecter(mazeXLim,mazeYLim);
		Vecter cellTopRight = new Vecter(mazeXLim, 0);
		Vecter cellBottomLeft = new Vecter(0, mazeYLim);
		
		assertNotNull(maze.getCell(cellTopLeft));
		assertNotNull(maze.getCell(cellBottomRight));
		assertNotNull(maze.getCell(cellTopRight));
		assertNotNull(maze.getCell(cellBottomLeft));
		
		//X out of bounds
		cellTopLeft = new Vecter(-1, 0);
		cellBottomRight = new Vecter(width, mazeYLim);
		cellTopRight = new Vecter(width, 0);
		cellBottomLeft = new Vecter(-1, mazeYLim);
		
		assertNull(maze.getCell(cellTopLeft));
		assertNull(maze.getCell(cellBottomRight));
		assertNull(maze.getCell(cellTopRight));
		assertNull(maze.getCell(cellBottomLeft));
		
		//Y out of bounds
		cellTopLeft = new Vecter(0, -1);
		cellBottomRight = new Vecter(mazeXLim, height);
		cellTopRight = new Vecter(mazeXLim, -1);
		cellBottomLeft = new Vecter(0, height);
		
		assertNull(maze.getCell(cellTopLeft));
		assertNull(maze.getCell(cellBottomRight));
		assertNull(maze.getCell(cellTopRight));
		assertNull(maze.getCell(cellBottomLeft));
	}

	@Test 
	public void testIsLinePassable(){
		Vecter start = new Vecter();
		Vecter end = new Vecter();
		
		start = new Vecter(0,0);
		assertTrue("Player is in sight", maze.isLinePassable(start, end));
		
		start = new Vecter(9,0);
		end = new Vecter(9, 9);
		assertTrue("Player is in sight", maze.isLinePassable(start, end));
		
		start = new Vecter(0,9);
		end = new Vecter(9,9);
		assertFalse("Player is not in sight", maze.isLinePassable(start, end));
		
		start = new Vecter(0,0);
		end = new Vecter(0, 9);
		assertFalse("Player is not in sight", maze.isLinePassable(start, end));
	}
}
