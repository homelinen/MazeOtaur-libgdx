package uk.calumgilchrist.mazeotaur;

import com.badlogic.gdx.math.Vector2;

public class Player extends Creature {

	public static final float SPEED_CONST = 0.12f;
	
	private float timeFromLastMove;

	private int changex;
	private int changey;
	
	/**
	 * Create the player
	 * 
	 * @param health How much damage can be dealt
	 * @param name Player name
	 * @param position Position on the board
	 */
	public Player(int health, String name, Vector2 position) {
		super(health, name, position);
		
		changex = 0;
		changey = 0;
		timeFromLastMove = 0;
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
	public void move(float deltaMult) {
		
		boolean hasChanged = changex != 0 || changey != 0;
		if (hasChanged && timeFromLastMove > SPEED_CONST) {
			setPosition(findNextPos());
			
			//Moved, reset timer
			timeFromLastMove = 0;
		} else {
			timeFromLastMove += deltaMult;
		}
		
		resetMovement();
	}
	
	/**
	 * Find the next point the player will reach
	 * 
	 * @return The next point the player will be going to
	 */
	public Vector2 findNextPos() {
		Vector2 curPos = getPosition().cpy();
		Vector2 changePos = new Vector2(changex, changey);
		
		return curPos.add(changePos);
	}
	
	public int getChangeX() {
		return changex;
	}
	
	public int getChangeY() {
		return changey;
	}

	/**
	 * Getter for timeFromLastMove
	 * 
	 * Only used in tests
	 * @param timeFromLastMove
	 */
	public void setTimeFromLastMove(float timeFromLastMove) {
		this.timeFromLastMove = timeFromLastMove;
	}
	
}
