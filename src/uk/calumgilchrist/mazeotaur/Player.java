package uk.calumgilchrist.mazeotaur;

public class Player extends Creature {

	public static final float SPEED_CONST = 0.12f;
	
	private int changex;
	private int changey;
	
	/**
	 * Create the player
	 * 
	 * @param health How much damage can be dealt
	 * @param name Player name
	 * @param position Position on the board
	 */
	public Player(int health, String name, Vecter position) {
		super(health, name, position, SPEED_CONST);
		
		changex = 0;
		changey = 0;
	}
	
	/**
	 * Update player position and other things
	 */
	public void update() {
		
	}
	
	/**
	 * Set delta movements to 0
	 */
	public void resetMovement() {
		changex = 0;
		changey = 0;
	}
	
	/**
	 * Where the player should move next on the x-axis
	 * @param x direction
	 */
	public void setChangeX(int x) {
		changex = findQuotient(x);
	}
	
	/**
	 * Where the player should move next on the y-axis
	 * @param y direction
	 */
	public void setChangeY(int y) {
		changey = findQuotient(y);
	}
	
	/**
	 * Take a number and reduce it to 1 or -1
	 * Simply performs n * 1/n
	 * Does not effect negative numbers.
	 * 
	 * @param input 
	 * @return Reduced number
	 */
	private int findQuotient(int input) {
		if (input != 0) {
			int out = 0;
			
			out = input/Math.abs(input);
			return out;
		
		} else {
			return input;
		}
	}

	/**
	 * Move the player by it's change in x
	 * @param deltaMult
	 * @param maze 
	 */
	public void move(float deltaTime) {
		
		boolean hasChanged = changex != 0 || changey != 0;
		if (hasChanged && canMove(deltaTime)) {
			setPosition(findNextPos());
		}
		
		resetMovement();
	}
	
	/**
	 * Find the next point the player will reach
	 * 
	 * @return The next point the player will be going to
	 */
	public Vecter findNextPos() {
		Vecter curPos = getPosition().cpy();
		Vecter changePos = new Vecter(changex, changey);
		
		return curPos.add(changePos);
	}
	
	public int getChangeX() {
		return changex;
	}
	
	public int getChangeY() {
		return changey;
	}
}
