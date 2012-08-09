package uk.calumgilchrist.mazeotaur;

import com.badlogic.gdx.math.Vector2;

public class Vecter extends Vector2 {

	private static final long serialVersionUID = 6971505377606459648L;

	public Vecter() {
		super();
	}

	public Vecter(Vector2 v) {
		super(v);
	}
	
	public Vecter(Vecter v) {
		set(v);
	}

	public Vecter(float x, float y) {
		super(x, y);
	}
	
	/**
	 * Compare one vector with another
	 * @param v Vector to compare with
	 * @return Whether or not the objects are equal
	 */
	public boolean equals(Vecter v) {
		return this.x == v.x && this.y == v.y;
	}

	@Override
	public Vecter cpy() {
		return new Vecter(this);
	}

	public Vecter set(Vecter v) {
		this.x = v.x;
		this.y = v.y;
		return this;
	}

	@Override
	public Vecter set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public Vecter sub(Vecter v) {
		this.x -= v.x;
		this.y -= v.y;
		return this;
	}
	
//	@Override
//	public Vecter sub(float x, float y) {
//		this.x -= x;
//		this.y -= y;
//		return this;
//	}

	public Vecter add(Vecter v) {
		this.x += v.x;
		this.y += v.y;
		return this;
	}

//	@Override
//	public Vecter add(float x, float y) {
//		this.x += x;
//		this.y += y;
//		return this;
//	}
//
//	@Override
//	public Vecter mul(float scalar) {
//		this.x = this.x * scalar;
//		this.y = this.y * scalar;
//		return this;
//	}
}