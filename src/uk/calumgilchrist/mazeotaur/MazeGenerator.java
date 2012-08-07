package uk.calumgilchrist.mazeotaur;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class MazeGenerator extends Maze {

	public MazeGenerator(int width, int height) {
		super(width, height);
	}
	
	/**
	 * Generate a maze
	 */
	public void createMaze() {
		Random rand = new Random();
		LinkedList<Vector2> walls = new LinkedList<Vector2>();
		int maxConnections = 1;
		
		int startX = 0;
		int startY = 0;
		
		Vector2 startCell = new Vector2(startX, startY);
		getCell(startCell).setPassable(true);
		addAdjacentWalls(walls, startCell);
		
		Cell tempCell = new Cell(false);
		
		for(Vector2 wall: walls) {
			Gdx.app.log("Initial Wall", wall.toString());
		}
		
		while (!walls.isEmpty()) {
			startCell = walls.remove(rand.nextInt(walls.size()));
			
			tempCell = getCell(startCell);
			if (!tempCell.isPassable() && checkAdjacent(startCell) <= maxConnections) {
				tempCell.setPassable(true);
				
				addAdjacentWalls(walls, startCell);
				Gdx.app.log("Added a Cell", startCell.toString());
			}
		}
	}
	
	/**
	 * Add the walls surrounding the given cell to the list
	 * @param walls List to add walls to
	 * @param cell Cell to check around
	 * @return
	 */
	public List<Vector2> addAdjacentWalls(List<Vector2> walls, Vector2 cell) {
		
		Iterator<Vector2> points = getSurroundingPoints(cell);
		
		Vector2 point = new Vector2();
		while (points.hasNext()) {	
			point = points.next();
			
			// Don't re-add cells
			if (!walls.contains(point) && !point.equals(cell)) {
				walls.add(point);
			}
		}
		return walls;
	}

	/**
	 * Retrieve a list of the points surrounding a given cell
	 * 
	 * TODO: Could return an Iterator
	 * @param point Look around this cell
	 * @return The cells connected to the given cell
	 */
	private Iterator<Vector2> getSurroundingPoints(Vector2 point) {
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
	 * Check around a cell to see it's connectivity
	 * @param pos
	 * @return
	 */
	private int checkAdjacent(Vector2 pos) {
		Iterator<Vector2> points = getSurroundingPoints(pos);
		
		int connected = 0;
		while (points.hasNext()) {
			if (getCell(points.next()).isPassable()) {
				connected++;
			}
		}
		
		Gdx.app.log("Connected", "" + connected);
		return connected;
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
}
