package uk.calumgilchrist.mazeotaur;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class InputController {
	private boolean downKeys[];
	
	public InputController() {
		downKeys = new boolean[255];
	}
	
	/**
	 * Reset polling
	 */
	public void clear() {
		//Is this better than setting all to false?
		downKeys = new boolean[255];
	}
	
	public void poll() {
		downKeys[Input.Keys.LEFT] = Gdx.input.isKeyPressed(Input.Keys.LEFT);
		downKeys[Input.Keys.RIGHT] = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
		downKeys[Input.Keys.UP] = Gdx.input.isKeyPressed(Input.Keys.UP);
		downKeys[Input.Keys.DOWN] = Gdx.input.isKeyPressed(Input.Keys.DOWN);
	}
	
	public boolean isKeyPressed(int key) {
		return downKeys[key];
	}
}
