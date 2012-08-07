package uk.calumgilchrist.mazeotaur;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class Enemy extends Creature {

	private List<Vector2> nodes;
	private boolean patrol;
	private int curNode;
	private int patrolLength;
	
	public Enemy(int health, String name, Vector2 position, int patrolLength) {
		super(health, name, position);
		nodes = new LinkedList<Vector2>();
		
		this.patrolLength = patrolLength;
		patrol = true;
		curNode = 0;
	}
	
	/**
	 * Set the path for the creature
	 * @param point - List of points in path
	 */
	public void setPath(List<Vector2> points) {
		
	}
	
	/**
	 * Move along path dependent on patrol mode
	 */
	public void moveNode() {
		if (patrol) {
			setPosition(nodes.get(curNode));
			curNode++;
			
			if (curNode >= patrolLength) {
				curNode = 0;
			}
		} else {
			setPosition(nodes.remove(0));
		}
	}
	
	public List<Vector2> getPath() {
		return nodes;
	}
}
