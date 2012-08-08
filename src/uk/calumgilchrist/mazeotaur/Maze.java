package uk.calumgilchrist.mazeotaur;

import java.util.Iterator;
import java.util.LinkedList;

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
		
		
		for (int y=0; y < height; y++) {
			for (int x=0; x < width; x++) {
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
	 * Retrieve a list of the points surrounding a given cell
	 * 
	 * TODO: Could return an Iterator
	 * @param point Look around this cell
	 * @return The cells connected to the given cell
	 */
	public Iterator<Vector2> getSurroundingPoints(Vector2 point) {
		LinkedList<Vector2> surroundingPoints = new LinkedList<Vector2>();
		
		int xStart = (int) point.x - 1;
		int yStart = (int) point.y - 1;
		
		int cellCheck = 3;
		int xEnd = xStart + cellCheck;
		int yEnd = yStart + cellCheck;
		
		for (int x = xStart; x < xEnd; x++) {
			for (int y = yStart; y < yEnd; y++) {
				//Check cell isn't out of bounds
				if (getCell(x, y) != null && !isDiagonal(x, y, point.x, point.y)) {
					surroundingPoints.add(new Vector2(x, y));
				}
			}
		}
		
		return surroundingPoints.iterator();
	}
	
	/**
	 * Find the closest valid grid vell
	 * @param closeTo
	 */
	public Vector2 findPassableCell(Vector2 closeTo) {
		LinkedList<Vector2> neighbourList = new LinkedList<Vector2>();
		
		neighbourList.add(closeTo);
		Vector2 neighbour;
		Iterator<Vector2> neighbours;
		
		while (!neighbourList.isEmpty()) {
			neighbour = neighbourList.remove();
			
			if (getCell(neighbour).isPassable()) {
				return neighbour;
			} else {
				//Find neighbours and add to the end of the neighbourList
				neighbours = getSurroundingPoints(closeTo);
				while (neighbours.hasNext()) {
					neighbourList.add(neighbours.next());
				}
			}
		}
		
		//Entire Maze is impassible
		return null;
	}
	
	/**
	 * Check if the given points are diagonal to the other
	 * @param x
	 * @param y
	 * @param pointX
	 * @param pointY
	 * @return
	 */
	private boolean isDiagonal(float x, float y, float pointX, float pointY) {
		
		if ((x != pointX && y != pointY)) {
			return true;
		} else {
			return false;
		}
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
