package uk.calumgilchrist.mazeotaur;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;

public class MazeOtaur implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	
	private MazeTemplate maze;
	
	private float cellHeight;
	private float cellWidth;
	
	private Texture passTex;
	private Texture wallTex;
	
	Player player;
	private Texture playerTex;
	
	@Override
	public void create() {
		
		Gdx.app.setLogLevel(Logger.DEBUG);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		camera.update();
		
		batch = new SpriteBatch();
		
		texture = new Texture(Gdx.files.internal("data/libgdx.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);
		
		sprite = new Sprite(region);
		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		sprite.setPosition(-sprite.getWidth()/2, -sprite.getHeight()/2);
		
		int mazeWidth = 9;
		int mazeHeight = 9;
		//Load and draw maze
		maze = new MazeTemplate(mazeWidth, mazeHeight);
		
		cellWidth = Gdx.graphics.getWidth() / (float) mazeWidth;
		cellHeight = Gdx.graphics.getHeight() / (float) mazeHeight;
		
		FileHandle mazeFile = Gdx.files.internal("openmaze.txt");
		if  (mazeFile.exists()) {
			maze.createMaze(mazeFile);
		} else {
			Gdx.app.error("MazeFile", "Maze file not found!");
		}
		Gdx.app.debug("\nMaze", maze.printMaze());
		
		// Set up maze textures
		// TODO: Don't load files like this
		passTex = new Texture(Gdx.files.internal("passable.png"));
		wallTex = new Texture(Gdx.files.internal("wall.png"));
		
		player = new Player(10, "Larry", new Vector2(0,0));
		setUpPlayerTexture();
		
	}
	
	/**
	 * Go through the given maze and display the passable cells
	 * @param maze
	 */
	public void drawMaze(MazeTemplate maze, SpriteBatch batch) {
		int width = maze.getWidth();
		int height = maze.getHeight();
			
		float yOffset = getScreenOffset();
		
		float xPos = 0;
		float yPos = 0;
		
		for (int x=0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				xPos = x * cellWidth;
				yPos = yOffset - (y * cellHeight);
				
				if (maze.getCell(x,y).isPassable()) {
					//Draw a white square
					batch.draw(passTex, xPos, yPos, cellWidth, cellHeight);
				} else {
					//Draw a black square
					batch.draw(wallTex, xPos, yPos, cellWidth, cellHeight);
				}
			}
		}
	}
	
	/**
	 * Render the player as a circle
	 * @param batch
	 */
	public void drawPlayer(SpriteBatch batch) {
		
		Vector2 playPos = player.getPosition().cpy().add(0, getScreenOffset());
		Gdx.app.log("PlayerPos", "" + playPos);
		
		batch.draw(playerTex, playPos.x, playPos.y);
	}

	public void setUpPlayerTexture() {
		
		//Should be size of cell
		int circleDiameter = (int) cellHeight;
		int circRadius = circleDiameter / 2;
		Pixmap circle = new Pixmap(circleDiameter, circleDiameter, Format.RGBA8888);
		
		circle.setColor(Color.GREEN);
		circle.fillCircle(circRadius, circRadius, circRadius);
		
		playerTex = new Texture(circle);
		circle.dispose();
	}
	
	/**
	 * Get the corresponding offset for the give y co-ord
	 * @param y
	 */
	public float getScreenOffset() {
		return Gdx.graphics.getHeight() - cellHeight;
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
		passTex.dispose();
		wallTex.dispose();
		playerTex.dispose();
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
			drawPlayer(batch);
		batch.end();
		Gdx.app.log("FPS", ""+ Gdx.graphics.getFramesPerSecond());
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
