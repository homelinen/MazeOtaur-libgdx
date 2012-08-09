package uk.calumgilchrist.mazeotaur.ai;

import com.badlogic.gdx.math.Vector2;

/**
 * Path 
 * @author homelinen
 *
 */
public class PathNode implements Comparable<PathNode> {

	private PathNode parent;
	private int cost;
	private Vector2 point;
	
	/**
	 * A node represents a point and the cost to that node
	 * @param point Point on map
	 * @param cost Cost to reach
	 */
	public PathNode(Vector2 point, int cost) {
		this.point = point;
		this.cost = cost;
		parent = null;
	}
	
	/**
	 * @param point Point on Map
	 * @param cost Cost to Reach
	 * @param parent Who lead you here
	 */
	public PathNode(Vector2 point, int cost, PathNode parent) {
		this.parent = parent;
		this.cost = cost;
		this.point = point;
	}

	/**
	 * Initialise an empty node at point (0,0) with no cost or parent
	 */
	public PathNode() {
		this.parent = null;
		this.point = new Vector2();
		this.cost = 0;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public Vector2 getPoint() {
		return point;
	}
	
	public PathNode getParent() {
		return parent;
	}

	/**
	 * Print the Node as a string
	 * Shows the point, cost and parent of the node
	 * @return String representing nide
	 */
	@Override
	public String toString() {
		String output = "PathNode: [ ";
		
		output += "Point: " + getPoint();
		output += ", Cost: " + getCost();
		output += ", Parent: " + getParent();
		output += " ]";
		
		return output;
	}
	
	/**
	 * Compare the cost of two PathNodes
	 * @param node The node to be compared
	 * @ return -1, 0 or 1 dependent on <, == or >
	 */
	@Override
	public int compareTo(PathNode node) {
		if (this.getCost() < node.getCost()) {
			return -1;
		} else if (this.getCost() > node.getCost()) {
			return 1;
		} else {
			return 0;
		}
	}

}
