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
		
		int read = 2;
		int x = 0;
		int y = 0;
		
		try {
			do {
				if (read == '1') {
					maze[x][y].setPassable(true);
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
		for (int i = 0; i < width - 1; i++) {
			for (int j = 0; j < height -1; j++) {
				// Set as impassible
				maze[i][j] = new Cell(passable);
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
		
		for (int i=0; i < width - 1; i++) {
			for (int j=0; j < height - 1; j++) {
				if (maze[i][j].isPassable()) {
					output += "1";
				} else {
					output += "0";
				}
			}
			output += " \n";
		}
		
		return output;
	}
}
