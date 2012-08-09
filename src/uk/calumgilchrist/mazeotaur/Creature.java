package uk.calumgilchrist.mazeotaur;

public class Creature {
	
	private int health;
	private String name;
	private Vecter position;
	
	private float timeFromLastMove;
	private float speed;
	
	/**
	 * The base constructor for a creature
	 * @param health How much damage can be taken
	 * @param name Unique Identifier for Creature
	 * @param position Where in space it is
	 */
	protected Creature(int health, String name, Vecter position, float speed) {
		super();
		this.health = health;
		this.name = name;
		this.position = position;
		this.timeFromLastMove = 0;
		this.speed = speed;
	}

	/**
	 * Decides whether the time from last move is enough
	 * @param deltaTime Change in time
	 * @return
	 */
	public boolean canMove(float deltaTime) {
		if (timeFromLastMove > speed) {
			timeFromLastMove = 0;
			return true;
		} else {
			timeFromLastMove += deltaTime;
			return false;
		}
	}
	
	public void setTimeFromLastMove(float timeFromLastMove) {
		this.timeFromLastMove = timeFromLastMove;
	}

	public int getHealth() {
		return health;
	}

	public String getName() {
		return name;
	}
	
	public Vecter getPosition() {
		return position;
	}

	public void setPosition(Vecter position) {
		this.position = position;
	}
}
