package uk.calumgilchrist.mazeotaur;

/**
 * One cell in the maze
 */
public class Cell {

	private boolean passable;
	
	public Cell(boolean passable) {
		this.passable = passable;
	}
	
	public boolean isPassable() {
		return passable;
	}
	
	public void setPassable(boolean passable) {
		this.passable = passable;
	}
}
