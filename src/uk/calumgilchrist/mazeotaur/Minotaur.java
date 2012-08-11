package uk.calumgilchrist.mazeotaur;

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
	 * @param speed how fast to move
	 */
	public Minotaur(int health, String name, Vecter position, float speed, int lOS) {
		super(health, name, position, speed, lOS);
	}

}
