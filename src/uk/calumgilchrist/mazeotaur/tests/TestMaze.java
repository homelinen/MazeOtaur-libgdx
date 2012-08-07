package uk.calumgilchrist.mazeotaur.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

import uk.calumgilchrist.mazeotaur.MazeTemplate;

public class TestMaze {

	private MazeTemplate maze;
	private int width;
	private int height;
	
	@Before
	public void setUp() {
		width = 9;
		height = 9;
		
		maze = new MazeTemplate(width, height);
	}
	
	@Test
	public void testMazeTemplate() {
		assertEquals(width, maze.getWidth());
		assertEquals(height, maze.getHeight());
	}

	@Test
	public void testGetCellVector2() {
		int mazeXLim = width - 1;
		int mazeYLim = height - 1;
		
		//Border
		Vector2 cellTopLeft= new Vector2(0,0);
		Vector2 cellBottomRight = new Vector2(mazeXLim,mazeYLim);
		Vector2 cellTopRight = new Vector2(mazeXLim, 0);
		Vector2 cellBottomLeft = new Vector2(0, mazeYLim);
		
		assertNotNull(maze.getCell(cellTopLeft));
		assertNotNull(maze.getCell(cellBottomRight));
		assertNotNull(maze.getCell(cellTopRight));
		assertNotNull(maze.getCell(cellBottomLeft));
		
		//X out of bounds
		cellTopLeft = new Vector2(-1, 0);
		cellBottomRight = new Vector2(width, mazeYLim);
		cellTopRight = new Vector2(width, 0);
		cellBottomLeft = new Vector2(-1, mazeYLim);
		
		assertNull(maze.getCell(cellTopLeft));
		assertNull(maze.getCell(cellBottomRight));
		assertNull(maze.getCell(cellTopRight));
		assertNull(maze.getCell(cellBottomLeft));
		
		//Y out of bounds
		cellTopLeft = new Vector2(0, -1);
		cellBottomRight = new Vector2(mazeXLim, height);
		cellTopRight = new Vector2(mazeXLim, -1);
		cellBottomLeft = new Vector2(0, height);
		
		assertNull(maze.getCell(cellTopLeft));
		assertNull(maze.getCell(cellBottomRight));
		assertNull(maze.getCell(cellTopRight));
		assertNull(maze.getCell(cellBottomLeft));
	}

}
