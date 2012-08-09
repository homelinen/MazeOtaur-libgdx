package uk.calumgilchrist.mazeotaur;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;

public class MazeGenerator extends Maze {

	public MazeGenerator(int width, int height) {
		super(width, height);
	}
	
	/**
	 * Generate a maze
	 */
	public void createMaze() {
		Random rand = new Random();
		LinkedList<Vecter> walls = new LinkedList<Vecter>();
		int maxConnections = 1;
		
		int startX = 0;
		int startY = 0;
		
		Vecter startCell = new Vecter(startX, startY);
		getCell(startCell).setPassable(true);
		addAdjacentWalls(walls, startCell);
		
		Cell tempCell = new Cell(false);
		
		for(Vecter wall: walls) {
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
	public List<Vecter> addAdjacentWalls(List<Vecter> walls, Vecter cell) {
		
		Iterator<Vecter> points = getSurroundingPoints(cell);
		
		Vecter point = new Vecter();
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
	 * Check around a cell to see it's connectivity
	 * @param pos
	 * @return
	 */
	private int checkAdjacent(Vecter pos) {
		Iterator<Vecter> points = getSurroundingPoints(pos);
		
		int connected = 0;
		while (points.hasNext()) {
			if (getCell(points.next()).isPassable()) {
				connected++;
			}
		}
		
		Gdx.app.log("Connected", "" + connected);
		return connected;
	}
}
