package uk.calumgilchrist.mazeotaur;

import java.io.BufferedReader;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Holds a template of a maze from a file
 * @author homelinen
 *
 */
public class MazeTemplate {

	private Cell[][] maze;
	private int width;
	private int height;
	
	/**
	 * Create a template maze
	 * 
	 * @param width - width of maze
	 * @param height - height of maze
	 */
	public MazeTemplate(int width, int height) {
		this.width = width;
		this.height = height;
		
		initMaze();
	}
	
	/**
	 * Generate a maze from a file
	 * @param mazeFile - File representing a maze
	 */
	public void createMaze(FileHandle mazeFile) {
		BufferedReader mazeReader = new BufferedReader(mazeFile.reader());
		
		int read = 0;
		int x = 0;
		int y = 0;
		
		//TODO: Add checks for maze size, or get size from file
		try {
			read = mazeReader.read();
					
			do {
				if (read == '1') {
					maze[y][x].setPassable(true);
				} else if (read == '\n') {
					// At end of line, move to new row
					y++;
					
					// Ensure x is 0 and not one on next loop
					x = -1;
				}
				x++;
				read = mazeReader.read();
			} while (read != -1);
			
		} catch (IOException e) {
			Gdx.app.log("ReadMaze", "IO Exception in MazeFile");
		}
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

	public int getWidth() {
		return maze.length;
	}
	
	public int getHeight() {
		return maze[0].length;
	}

	/**
	 * Return the cell from the array at x, y
	 * @param x
	 * @param y
	 * @return
	 */
	public Cell getCell(int x, int y) {
		
		if (x >= 0 && x < getHeight() && y >= 0 && y < getHeight()) {
			return maze[x][y];
		} else {
			Gdx.app.log("GetCell", "Non valid maze co-ord");
			return null;
		}
	}
}
