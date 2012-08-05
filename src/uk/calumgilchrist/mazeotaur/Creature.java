package uk.calumgilchrist.mazeotaur;

import com.badlogic.gdx.math.Vector2;

public class Creature {
	
	private int health;
	private String name;
	private Vector2 position;
	
	public Creature(int health, String name, Vector2 position) {
		super();
		this.health = health;
		this.name = name;
		this.position = position;
	}

	public int getHealth() {
		return health;
	}

	public String getName() {
		return name;
	}
	
	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}
}
