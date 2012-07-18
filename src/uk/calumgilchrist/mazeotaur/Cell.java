package uk.calumgilchrist.mazeotaur;

/**
 * One cell or tile in a maze
 */
public class Cell {

	private boolean passable;
	
	/**
	 * Create a new cell
	 * @param passable - Wall or floor?
	 */
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
