package uk.calumgilchrist.mazeotaur;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Holds a template of a maze from a file
 * @author homelinen
 *
 */
public class MazeTemplate extends Maze {

	/**
	 * Create a template maze
	 * 
	 * @param width - width of maze
	 * @param height - height of maze
	 */
	public MazeTemplate(int width, int height) {
		super(width, height);
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
					getCell(x, y).setPassable(true);
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
	 * Generate a maze from a list of passable co-ords
	 * @param points
	 */
	public void createMaze(List<Vecter> points) {
		
		Vecter tempVec = new Vecter();
		
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				tempVec = new Vecter(x, y);
				if (points.contains(tempVec)) {
					getCell(tempVec).setPassable(true);
				}
			}
		}
	}
}