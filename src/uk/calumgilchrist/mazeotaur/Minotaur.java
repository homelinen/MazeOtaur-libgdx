package uk.calumgilchrist.mazeotaur;

import com.badlogic.gdx.math.Vector2;

/**
 * Large Creature, normally wielding a hammer
 * @author homelinen
 *
 */
public class Minotaur extends Enemy {

	/**
	 * New Minotaur 
	 * @param health how much damage can be taken
	 * @param name Unique name
	 * @param position where the creature is
	 * @param patrolLength how many squares to patrol
	 */
	public Minotaur(int health, String name, Vector2 position, int patrolLength) {
		super(health, name, position, patrolLength);
	}

}
