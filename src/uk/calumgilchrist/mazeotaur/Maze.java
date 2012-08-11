package uk.calumgilchrist.mazeotaur;

import java.util.Iterator;
import java.util.LinkedList;

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
	public Iterator<Vecter> getSurroundingPoints(Vecter point) {
		LinkedList<Vecter> surroundingPoints = new LinkedList<Vecter>();
		
		int xStart = (int) point.x - 1;
		int yStart = (int) point.y - 1;
		
		int cellCheck = 3;
		int xEnd = xStart + cellCheck;
		int yEnd = yStart + cellCheck;
		
		for (int x = xStart; x < xEnd; x++) {
			for (int y = yStart; y < yEnd; y++) {
				//Check cell isn't out of bounds
				if (getCell(x, y) != null && !isDiagonal(x, y, point.x, point.y)) {
					surroundingPoints.add(new Vecter(x, y));
				}
			}
		}
		
		return surroundingPoints.iterator();
	}
	
	/**
	 * Find the closest valid grid cell
	 * @param closeTo
	 */
	public Vecter findPassableCell(Vecter closeTo) {
		LinkedList<Vecter> neighbourList = new LinkedList<Vecter>();
		
		neighbourList.add(closeTo);
		Vecter neighbour;
		Iterator<Vecter> neighbours;
		
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
		
		//Entire Maze is inpassible
		return null;
	}
	
	/**
	 * Check if the given points are diagonal to each other
	 * 
	 * TODO: Add a method for dealing with Vectors
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
	 * Is it possible to go straight between two points
	 * @param start
	 * @param end
	 * @return
	 */
	public boolean isLinePassable(Vecter start, Vecter end) {
		
		float lowCoord = 0;
		float highCoord = 0;
		float fixedCoord = 0;
		
		if (start.equals(end)) {
			return getCell(start).isPassable();
		} else if (start.x == end.x) {
			fixedCoord = start.x;
			
			if (start.y > end.y) {
				highCoord = start.y;
				lowCoord = end.y;
			} else if (start.y < end.y) {
				highCoord = end.y;
				lowCoord = start.y;
			} 			
			
			return checkLine(new Vecter(fixedCoord, lowCoord), new Vecter(fixedCoord, highCoord));
			
		} else if (start.y == end.y) {
			fixedCoord = start.y;
			
			if (start.x > end.x) {
				highCoord = start.x;
				lowCoord = end.x;
			} else if (start.x < end.x) {
				highCoord = end.x;
				lowCoord = start.x;
			} 			
			
			return checkLine(new Vecter(lowCoord, fixedCoord), new Vecter(highCoord, fixedCoord));
		} else {
			//Lines are diagonal
			return false;
		}
	}
		
	/**
	 * Check between two points to ensure they are passable
	 * 
	 * Does not check whether the two points lie on the same axis
	 * @param start
	 * @param end
	 * @return
	 */
	private boolean checkLine(Vecter start, Vecter end) {
		for (int x = (int) start.x; x <= (int) end.x; x++) {
			for (int y = (int) start.y; y <= (int) end.y; y++) {
				if (!getCell(x, y).isPassable()) {
					return false;
				}
			}
		}
		return true;
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
	public Cell getCell(Vecter pos) {
		return getCell((int) pos.x, (int) pos.y);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
