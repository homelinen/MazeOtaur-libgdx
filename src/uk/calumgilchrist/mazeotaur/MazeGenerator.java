package uk.calumgilchrist.mazeotaur;

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
		
		
		int middleX = getWidth() / 2;
		int middleY = getHeight() / 2;
		
		Vector2 startCell = new Vector2(middleX, middleY);
		addAdjacentWalls(walls, startCell);
		
		Cell tempCell = new Cell(false);
		
		for(Vector2 wall: walls) {
			Gdx.app.log("Initial Wall", wall.toString());
		}
		
		while (!walls.isEmpty()) {
			startCell = walls.remove(rand.nextInt(walls.size()));
			
			tempCell = getCell(startCell);
			if (!tempCell.isPassable()) {
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
		
		int xStart = (int) cell.x - 1;
		int yStart = (int) cell.y - 1;
		
		int cellCheck = 3;
		int xEnd = xStart + cellCheck;
		int yEnd = yStart + cellCheck;
		
		Vector2 tempCell = new Vector2();
		for (int x = xStart; x < xEnd; x++) {
			for (int y = yStart; y < yEnd; y++) {
				
				//Check cell isn't out of bounds
				if (getCell(x, y) != null && !isDiagonal(x, y, cell.x, cell.y)) {
					tempCell = new Vector2(x, y);
					
					// Don't re-add cells
					if (!walls.contains(tempCell) && !tempCell.equals(cell)) {
						walls.add(tempCell);
					}
				}
			}
		}
		return walls;
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
		
		if (x != pointX && y != pointY) {
			return true;
		} else {
			return false;
		}
	}
}
