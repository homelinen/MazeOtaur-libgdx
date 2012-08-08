package uk.calumgilchrist.mazeotaur.ai;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import uk.calumgilchrist.mazeotaur.Enemy;
import uk.calumgilchrist.mazeotaur.Maze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Controls the enemies movement and positioning in the map
 * @author homelinen
 *
 */
public class AIManager {

	private List<Enemy> creatures;
	
	/**
	 * Create a new manager with an empty creature list
	 */
	public AIManager() {
		creatures = new LinkedList<Enemy>();
	}
	
	/**
	 * Add a creature to the management list and set it's path
	 * @param enemy The creature to add
	 * @param maze The maze to patrol
	 * @param goal Where the patrol should end
	 */
	public void addCreature(Enemy enemy, Maze maze, Vector2 goal) {
		
		creatures.add(enemy);
		creatures.get(creatures.size() - 1).setPath(findPath(maze, enemy.getPosition(), goal));
	}
	
	/**
	 * Update positions of creature, etc
	 */
	public void update() {
		for (Enemy creature: creatures) {
			creature.moveNode();
		}
	}
	
	/**
	 * Find a path to the point
	 * @param The maze to do operations on
	 * @param start beginning point
	 * @param goal Final point to reach
	 * @return List of points to follow
	 */
	public List<Vector2> findPath(Maze maze, Vector2 start, Vector2 goal) {
		
		PriorityQueue<PathNode> open = new PriorityQueue<PathNode>();
		LinkedList<PathNode> closed = new LinkedList<PathNode>();
		
		open.add(new PathNode(start, getPathCost(start, goal)));
		
		PathNode curNode;
		Iterator<Vector2> neighbours;
		Vector2 tempNeighbour = new Vector2();
		
		int curcost = 0;
		PathNode tempNode = new PathNode();
		boolean inOpen = false;
		boolean inClosed = false;
		
		while (!open.isEmpty() && !open.element().getPoint().equals(goal)) {
			
			curNode = open.remove();
			closed.add(curNode);
			
			neighbours = maze.getSurroundingPoints(curNode.getPoint());
			
			while (neighbours.hasNext()) {
				curcost = curNode.getCost() + 1;
				
				tempNeighbour = neighbours.next();
				
				if (maze.getCell(tempNeighbour).isPassable()) {
					tempNode = getPointInNode(open, tempNeighbour); 
					inOpen = ( tempNode != null );
					if (inOpen && (tempNode.getCost() > curcost)) {
						open.remove(tempNode);
					}
					
					tempNode = getPointInNode(closed, tempNeighbour);
					inClosed = tempNode != null;
					
					if (inClosed && tempNode.getCost() > curcost) {
						closed.remove(tempNode);
					}
					
					if (!inOpen && !inClosed) {
						open.add(new PathNode(tempNeighbour, curcost, curNode));
					}
				}
			}
		}
		
		if (!open.isEmpty()) {
			//Add the head element
			closed.add(open.element());
		
			//Free up some space, don't need anymore
			open.clear();
		}
		
		//TODO closed list could be cleared
		return findRoute(closed.get(closed.size() - 1), start);
	}
	
	/**
	 * Slow O(n) search to see if a point is present in a nodeList
	 * Returns the cost, or -1 if not in list
	 * @param nodeList List of PathNodes to search
	 * @param point Point of comparison
	 * @return The cost of the point in the list
	 */
	public PathNode getPointInNode(Collection<PathNode> nodeList, Vector2 point) {
		Iterator<PathNode> nodes = nodeList.iterator();
		
		PathNode tempNode;
		while (nodes.hasNext()) {
			tempNode = nodes.next();
			if (tempNode.getPoint().equals(point)) {
				
				return tempNode;
			}
		}
		
		return null;
	}
	
	/**
	 * Find the route by following the parents of the nodes in the List
	 * @param The final node, with parents
	 * @param start Where you began
	 * @return A shortened list of the path needed to be taken
	 */
	public List<Vector2> findRoute(PathNode goal, Vector2 start) {
		LinkedList<Vector2> route = new LinkedList<Vector2>();
		
		route.addFirst(goal.getPoint());
		
		PathNode tempNode = goal.getParent();
		route.addFirst(tempNode.getPoint());
		
		while (!tempNode.getPoint().equals(start) && tempNode != null) {
			tempNode = tempNode.getParent();
			route.addFirst(tempNode.getPoint());
		}
		
		return route;
	}
	
	/**
	 * Find the cost between start and end
	 * @param start Initial Point
	 * @param end Goal Point
	 * @return The sum of the distance to the point
	 */
	public int getPathCost(Vector2 start, Vector2 end) {
		Vector2 sum = start.cpy().sub(end);
		int cost = 0;
		
		cost = (int) (Math.abs(sum.x) + Math.abs(sum.y));
		
		return cost;
	}

	/**
	 * Return the number of managed creatures
	 * @return
	 */
	public int getCreatureCount() {
		return creatures.size();
	}

	/**
	 * Get creatures
	 * @return All creatures in a list
	 */
	public List<Enemy> getCreatures() {
		return creatures;
	}
}
