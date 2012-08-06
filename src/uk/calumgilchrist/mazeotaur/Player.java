package uk.calumgilchrist.mazeotaur;

import com.badlogic.gdx.math.Vector2;

public class Player extends Creature {

	public static final float SPEED_CONST = 0.08f;
	
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
	 * Where the player should move next on the x-axis
	 * @param x direction
	 */
	public void setChangeX(int x) {
		changex = x;
	}
	
	/**
	 * Where the player should move next on the y-axis
	 * TODO: Capture y > 1 or y < -1
	 * TODO: Use an enum rather than an Int, or simply a static constant
	 * 
	 * @param y direction
	 */
	public void setChangeY(int y) {
		changey = y;
	}

	/**
	 * Move the player by it's change in x
	 * @param deltaMult
	 * @param maze 
	 */
	public void move(float deltaMult) {
		
		if (timeFromLastMove > SPEED_CONST && (changex != 0 || changey != 0)) {
			
			Vector2 curPos = getPosition().cpy();
			Vector2 changePos = new Vector2(changex, changey);
			setPosition(curPos.add(changePos));
			
			//Moved, reset timer
			timeFromLastMove = 0;
		} else {
			timeFromLastMove += deltaMult;
		}
		
		// Reset delta x and y
		changex = 0;
		changey = 0;
	}
}
