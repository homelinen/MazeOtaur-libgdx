package uk.calumgilchrist.mazeotaur;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

/**
 * An enemy is a creature with malicious intent against the player.
 * 
 * Enemies follow a pre-set path given to them.
 * @author homelinen
 *
 */
public class Enemy extends Creature {

	private List<Vector2> nodes;
	private boolean patrol;
	private int curNode;
	private boolean reverse;
	
	/**
	 * Create an enemy
	 * @param health how much damage can be taken
	 * @param name Unique name
	 * @param position where the creature is
	 * @param patrolLength how many squares to patrol
	 */
	public Enemy(int health, String name, Vector2 position, float speed) {
		super(health, name, position, speed);
		nodes = new LinkedList<Vector2>();
		
		patrol = true;
		curNode = 0;
		reverse = false;
	}
	
	/**
	 * Set the path for the creature and start on the path
	 * @param point - List of points in path
	 */
	public void setPath(List<Vector2> points) {
		curNode = 0;
		nodes = points;
		
		setPosition(nodes.get(0));
	}
	
	/**
	 * Move along path dependent on patrol mode
	 * @param deltaTime Time from last screen update
	 */
	public void moveNode(float deltaTime) {
		if(canMove(deltaTime)) {
			if (patrol) {
				if (!reverse) {
					
					if (curNode >= nodes.size() - 1) {
						reverse = true;
					} else {
						curNode++;
					}
				} else {
					
					if (curNode <= 0) {
						reverse = false;
					} else {
						curNode--;
					}
				}
				setPosition(nodes.get(curNode));
			} else {
				setPosition(nodes.remove(0));
			}
		}
	}
	
	public List<Vector2> getPath() {
		return nodes;
	}
}
