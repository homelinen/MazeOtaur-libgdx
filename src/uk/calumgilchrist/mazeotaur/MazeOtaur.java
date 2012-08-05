package uk.calumgilchrist.mazeotaur;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Logger;

public class MazeOtaur implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	
	private MazeTemplate maze;
	
	private Texture passTex;
	private Texture wallTex;
	
	@Override
	public void create() {
		
		Gdx.app.setLogLevel(Logger.DEBUG);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
		batch = new SpriteBatch();
		
		texture = new Texture(Gdx.files.internal("data/libgdx.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);
		
		sprite = new Sprite(region);
		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
		
		//Load and draw maze
		maze = new MazeTemplate(10, 10);
		
		FileHandle mazeFile = Gdx.files.internal("amaze.txt");
		if  (mazeFile.exists()) {
			maze.createMaze(mazeFile);
		} else {
			Gdx.app.error("MazeFile", "Maze file not found!");
		}
		Gdx.app.debug("\nMaze", maze.printMaze());
		
	}
	
	/**
	 * Go through the given maze and display the passable cells
	 * @param maze
	 */
	public void drawMaze(MazeTemplate maze, SpriteBatch batch) {
		int width = maze.getWidth();
		int height = maze.getHeight();
		
		// Set up textures
		// TODO: Don't load files like this
		passTex = new Texture(Gdx.files.internal("passable.png"));
		wallTex = new Texture(Gdx.files.internal("wall.png"));
		
		int cellWidth = Gdx.graphics.getWidth() / width;
		int cellHeight = Gdx.graphics.getHeight() / height;
		
		for (int x=0; x < width - 1; x++) {
			for (int y = 0; y < height - 1; y++) {
				if (maze.getCell(x,y).isPassable()) {
					//Draw a white square
					batch.draw(passTex, x * cellWidth, y * cellHeight, cellWidth, cellHeight);
				} else {
					//Draw a black square
					batch.draw(wallTex, x * cellWidth, y * cellHeight, cellWidth, cellHeight);
				}
			}
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
		passTex.dispose();
		wallTex.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(0.5f, 0.1f, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		//sprite.draw(batch);
		
		drawMaze(maze, batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
