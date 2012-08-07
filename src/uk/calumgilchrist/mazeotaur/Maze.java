package uk.calumgilchrist.mazeotaur;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Maze {

	private int width;
	private int height;
	
	private Cell[][] maze;
	
	/**
	 * Maze constructor
	 * @param width How many cells wide
	 * @param height How many cells tall
	 */
	public Maze(int width, int height) {
		this.width = width;
		this.height = height;
		
		maze = new Cell[width][height];
		
		initMaze();
	}
	
	/**
	 * Initialise the maze
	 */
	private void initMaze() {
		maze = new Cell[width][height];
		
		boolean passable = false;
		
		// Go through all cells
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				// Set as impassible
				maze[x][y] = new Cell(passable);
			}
		}
	}
	
	/**
	 * Create a string representation of the maze
	 * Key:
	 *   0 - Wall
	 *   1 - Floor
	 *   
	 * @return - MultiLine string representing a maze
	 */
	public String printMaze() {
		String output = "";
		
		for (int x=0; x < width; x++) {
			for (int y=0; y < height; y++) {
				if (maze[x][y].isPassable()) {
					output += "1";
				} else {
					output += "0";
				}
			}
			output += " \n";
		}
		
		return output;
	}
	
	/**
	 * Return the cell from the array at x, y
	 * @param x
	 * @param y
	 * @return The cell at position (x, y)
	 */
	public Cell getCell(int x, int y) {
		
		if (x >= 0 && x < getWidth() && y >= 0 && y < getHeight()) {
			return maze[x][y];
		} else {
			Gdx.app.log("GetCell", "Non valid maze co-ord: " + x + ", " + y);
			return null;
		}
	}
	
	/**
	 * Return the cell from the given vector
	 * @param pos The position to check
	 * @return The cell at pos
	 */
	public Cell getCell(Vector2 pos) {
		return getCell((int) pos.x, (int) pos.y);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
